package com.opendata.zurich.gcs;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.beam.runners.direct.DirectRunner;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
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

import com.opendata.zurich.model.FullRide;
import com.opendata.zurich.model.GeoJsonMultiple;
import com.opendata.zurich.model.RideBreakPointStop;

/**
 * Process to create a FullRide file from denormalized ride table
 * 
 * @author Osmar
 * 
 */
public class FullRideProcess {
	
	public static void main(String[] args) {

		PipelineOptions options = PipelineOptionsFactory.create();
		options.setRunner(DirectRunner.class);
		Pipeline p = Pipeline.create(options);

		//DenormalizedRides - Read CSV Files
		PCollection<String> rides = 
				p.apply("Read DenormalizedRides files ", TextIO.read()
						.from("D:\\Poc_Analytics\\_LOCAL\\denormalizedTable.csv"));

		//Transform to Object
		PCollection<KV<Long, RideBreakPointStop>> rideObject = rides.apply("ParDo to Object", ParDo.of(new DoFn<String, KV<Long, RideBreakPointStop>>() {
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
					c.output(KV.of(ride.getRideId(), ride));
				}
			}
		}));

		final TupleTag<RideBreakPointStop> rideTag = new TupleTag<RideBreakPointStop>();
		
		PCollection<KV<Long, CoGbkResult>> rideCoGroupBy =
				KeyedPCollectionTuple.of(rideTag, rideObject)
				.apply(CoGroupByKey.create());

		//Transform Grouped Ride to a Full Ride
		PCollection<String> fullRideCsv = 
				rideCoGroupBy.apply("ParDo Full Ride", ParDo.of(new DoFn<KV<Long, CoGbkResult>, String>() {
					private static final long serialVersionUID = 1L;
					@ProcessElement
					public void processElement(ProcessContext c) {
						KV<Long, CoGbkResult> e = c.element();
						
						Long rideId = e.getKey();
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
						
						c.output(fullRide.toCsv());						
					}
				}));
		
		//Write CSV File
		fullRideCsv.apply("Write CSV File", TextIO.write().to("fullRide.csv").withoutSharding());

		p.run().waitUntilFinish();

	}

}


