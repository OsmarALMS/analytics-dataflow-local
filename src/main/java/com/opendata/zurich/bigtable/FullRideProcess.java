package com.opendata.zurich.bigtable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.gcp.bigtable.BigtableIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.join.CoGbkResult;
import org.apache.beam.sdk.transforms.join.CoGroupByKey;
import org.apache.beam.sdk.transforms.join.KeyedPCollectionTuple;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TupleTag;
import org.apache.beam.vendor.guava.v20_0.com.google.common.collect.Lists;

import com.google.api.services.bigquery.model.TableRow;
import com.google.bigtable.v2.Mutation;
import com.google.protobuf.ByteString;
import com.opendata.zurich.model.FullRide;
import com.opendata.zurich.model.GeoJsonMultiple;
import com.opendata.zurich.model.RideBreakPointStop;
import com.opendata.zurich.options.BigTableEventsOptions;

/**
 * Process to create a FullRide file from denormalized ride table
 * 
 * @author Osmar
 * 
 */
public class FullRideProcess {
	
	static void runTypeModel(BigTableEventsOptions options) {

		Pipeline p = Pipeline.create(options);

		//DenormalizedRides - Read CSV Files
		PCollection<String> rides = 
				p.apply("Read DenormalizedRides files ", TextIO.read()
						.from(options.getInputDenormalizedRideFile()));

		//Transform to Object
		PCollection<KV<String, RideBreakPointStop>> rideObject = rides.apply("ParDo to Object", ParDo.of(new DoFn<String, KV<String, RideBreakPointStop>>() {
			private static final long serialVersionUID = 1L;
			@ProcessElement
			public void processElement(ProcessContext c) {
				if(!c.element().trim().isEmpty()) {
					List<String> lineContent = Arrays.asList(c.element().split(";"));
					RideBreakPointStop ride = new RideBreakPointStop(
							Long.parseLong(lineContent.get(0)), lineContent.get(1), Long.parseLong(lineContent.get(2)), Long.parseLong(lineContent.get(3)), 
							Long.parseLong(lineContent.get(4)), Long.parseLong(lineContent.get(5)), lineContent.get(6), lineContent.get(7), 
							Long.parseLong(lineContent.get(8)), Long.parseLong(lineContent.get(9)), Long.parseLong(lineContent.get(10)), 
							lineContent.get(11), lineContent.get(12), Long.parseLong(lineContent.get(13)), 
							Long.parseLong(lineContent.get(14)), Long.parseLong(lineContent.get(15)), lineContent.get(16), 
							lineContent.get(17), lineContent.get(18), lineContent.get(19), 
							Long.parseLong(lineContent.get(20)), lineContent.get(21), lineContent.get(22), 
							lineContent.get(23), lineContent.get(24));
					
					c.output(KV.of(ride.getRideId()+"~~"+ride.getOperationDate(), ride));
				}
			}
		}));

		final TupleTag<RideBreakPointStop> rideTag = new TupleTag<RideBreakPointStop>();
		
		PCollection<KV<String, CoGbkResult>> rideCoGroupBy =
				KeyedPCollectionTuple.of(rideTag, rideObject)
				.apply(CoGroupByKey.create());

		//Transform Grouped Ride to a Full Ride
		PCollection<TableRow> fullRideCsv = 
				rideCoGroupBy.apply("ParDo Full Ride", ParDo.of(new DoFn<KV<String, CoGbkResult>, TableRow>() {
					private static final long serialVersionUID = 1L;
					@ProcessElement
					public void processElement(ProcessContext c) {
						KV<String, CoGbkResult> e = c.element();
						
						Long rideId = Long.parseLong(e.getKey().split("~~")[0]);
						
						List<RideBreakPointStop> listRideBreakPoint = Lists.newArrayList(e.getValue().getAll(rideTag));
						Collections.sort(listRideBreakPoint);
						
						List<List<BigDecimal>> geoCoordinates = new ArrayList<List<BigDecimal>>();
						for(int i = 0; i < listRideBreakPoint.size(); i++) {
							if(i == listRideBreakPoint.size()-1) {
								BigDecimal latFrom = (listRideBreakPoint.get(i).getAfterLatitude() != null && !listRideBreakPoint.get(i).getAfterLatitude().equals("")) ?
									new BigDecimal(listRideBreakPoint.get(i).getAfterLatitude()) : new BigDecimal(0);
								BigDecimal longFrom = (listRideBreakPoint.get(i).getAfterLongitude() != null && !listRideBreakPoint.get(i).getAfterLongitude().equals("")) ?
									new BigDecimal(listRideBreakPoint.get(i).getAfterLongitude()) : new BigDecimal(0);
								geoCoordinates.add(Arrays.asList(new BigDecimal[]{latFrom, longFrom}));
							}else {
								BigDecimal latFrom = (listRideBreakPoint.get(i).getFromLatitude() != null && !listRideBreakPoint.get(i).getFromLatitude().equals("")) ?
									new BigDecimal(listRideBreakPoint.get(i).getFromLatitude()) : new BigDecimal(0);
								BigDecimal longFrom = (listRideBreakPoint.get(i).getFromLongitude() != null && !listRideBreakPoint.get(i).getFromLongitude().equals("")) ?
									new BigDecimal(listRideBreakPoint.get(i).getFromLongitude()) : new BigDecimal(0);
								geoCoordinates.add(Arrays.asList(new BigDecimal[]{latFrom, longFrom}));
							}
						}
						
						FullRide fullRide = new FullRide(
								rideId, listRideBreakPoint.get(0).getVehicleNumber(), listRideBreakPoint.get(0).getCourseNumber(), 
								Long.parseLong(String.valueOf(listRideBreakPoint.size())), listRideBreakPoint.get(0).getDtStopFrom(), 
								listRideBreakPoint.get(0).getTimeStopFromReal(), listRideBreakPoint.get(listRideBreakPoint.size()-1).getDtStopAfter(), 
								listRideBreakPoint.get(listRideBreakPoint.size()-1).getTimeStopAfterReal(), 
								(listRideBreakPoint.get(listRideBreakPoint.size()-1).getTimeStopAfterReal() - listRideBreakPoint.get(0).getTimeStopFromReal()));
						
						GeoJsonMultiple geoJson = new GeoJsonMultiple();
						geoJson.setGeometry("LineString", geoCoordinates);
						fullRide.setGeoJson(geoJson);
						
						c.output(fullRide.toTableRow());				
					}
				}));
		
		PCollection<KV<ByteString, Iterable<Mutation>>> fullRideMutation =
				fullRideCsv.apply("ParDo TableRow to Mutation", ParDo.of(new DoFn<TableRow, KV<ByteString, Iterable<Mutation>>>() {
					private static final long serialVersionUID = 1L;
					@ProcessElement
					public void processElement(ProcessContext c) {
						c.output(Utils.makeWrite(c.element().get("rideId").toString(), c.element(), null));
					}
				}));

		fullRideMutation.apply("Write TO BigTable",
				BigtableIO.write()
				.withProjectId("celfocus-vfpt-poc-analytics")
				.withInstanceId(options.getBigTableInstance())
				.withTableId(options.getBigTableTable()));
		
		p.run().waitUntilFinish();
		
	}
	
	public static void main(String[] args) {
		BigTableEventsOptions options =
				PipelineOptionsFactory.fromArgs(args).withValidation().as(BigTableEventsOptions.class);

		runTypeModel(options);
	}

}


