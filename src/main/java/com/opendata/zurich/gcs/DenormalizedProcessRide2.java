package com.opendata.zurich.gcs;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.beam.runners.direct.DirectRunner;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.View;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionView;

import com.opendata.zurich.model.BreakPointStop;
import com.opendata.zurich.model.GeoJsonMultiple;
import com.opendata.zurich.model.Ride;
import com.opendata.zurich.model.RideBreakPointStop;

/**
 * Process to create a Denormalized table from Zurich Travel Dalyes (Ride - BreakPointStop)
 * https://data.stadt-zuerich.ch/dataset/vbz_fahrzeiten_ogd
 * 
 * @author Osmar
 * 
 */
public class DenormalizedProcessRide2 {

	public static void main(String[] args) {

		PipelineOptions options = PipelineOptionsFactory.create();
		options.setRunner(DirectRunner.class);
		Pipeline p = Pipeline.create(options);

		//Rides - Read CSV Files
		PCollection<String> rides = 
				p.apply("Read Rides files ", TextIO.read()
						.from("D:\\Poc_Analytics\\_LOCAL\\ride_teste.csv"));

		//BreakPoint - Read CSV Files
		PCollection<String> breakPointStop = 
				p.apply("Read BreakPoints file", TextIO.read()
						.from("D:\\Poc_Analytics\\_LOCAL\\haltepunkt-haltestelle.csv"));

		//Delays - Transform Cleanse Object KV >FROM<
		PCollection<Ride> rideFromKv = rides.apply("ParDo Rides Cleanse|Object|KV From", ParDo.of(new DoFn<String, Ride>() {
			private static final long serialVersionUID = 1L;
			@ProcessElement
			public void processElement(ProcessContext c) { 
				if(!c.element().trim().isEmpty() && !c.element().contains("linie")) {
					List<String> lineContent = Arrays.asList(c.element().split(","));
					Ride ride = new Ride(
							lineContent.get(2),Long.parseLong(lineContent.get(3)),Long.parseLong(lineContent.get(4)), 
							Long.parseLong(lineContent.get(5)),Long.parseLong(lineContent.get(6)), lineContent.get(8), 
							lineContent.get(9),Long.parseLong(lineContent.get(10)),Long.parseLong(lineContent.get(11)), 
							Long.parseLong(lineContent.get(15)), lineContent.get(17), lineContent.get(18), 
							Long.parseLong(lineContent.get(19)),Long.parseLong(lineContent.get(20)), 
							Long.parseLong(lineContent.get(23)), Long.parseLong(lineContent.get(32)), Long.parseLong(lineContent.get(33)));
					c.output(ride);
				}
			}
		}));

		//BreakPointStop - Transform Object KV
		PCollection<KV<Long, BreakPointStop>> beakPointStopKv = breakPointStop.apply("ParDo BreakPointStop Object|KV", 
				ParDo.of(new DoFn<String, KV<Long, BreakPointStop>>() {
					private static final long serialVersionUID = 1L;
					@ProcessElement
					public void processElement(ProcessContext c) { 
						if(!c.element().trim().isEmpty()) {
							List<String> lineContent = Arrays.asList(c.element().split(";"));
							BreakPointStop breakPointStop = 
									new BreakPointStop(Long.parseLong(lineContent.get(0)), 
											lineContent.get(1), lineContent.get(2), 
											lineContent.get(3), lineContent.get(4));
							c.output(KV.of(breakPointStop.getBreakpointId(), breakPointStop));
						}
					}
				}));
		PCollectionView<Map<Long,BreakPointStop>> beakPointStopKvView = beakPointStopKv.apply(View.<Long,BreakPointStop>asMap());
		
		PCollection<String> rideDenormalized = rideFromKv.apply("ParDo DenormalizedRide", ParDo.of(new DoFn<Ride, String>() {
			private static final long serialVersionUID = 1L;
			@ProcessElement
			public void processElement(ProcessContext c) { 

				Map<Long, BreakPointStop> breakPointMap = c.sideInput(beakPointStopKvView);
				Ride ride = c.element();
				
				BreakPointStop breakPointFrom = breakPointMap.get(ride.getBreakpointIdFrom()) != null ? breakPointMap.get(ride.getBreakpointIdFrom()) : new BreakPointStop();
				BreakPointStop breakPointAfter = breakPointMap.get(ride.getBreakpointIdAfter()) != null ? breakPointMap.get(ride.getBreakpointIdAfter()) : new BreakPointStop();
				
				RideBreakPointStop rideBreakPointStop = 
					new RideBreakPointStop(ride.getRideId(), ride.getOperationDate(), ride.getVehicleNumber(),
							ride.getCourseNumber(), ride.getSequenceStop(), ride.getStopIdFrom(), 
							ride.getStopCodeFrom(), ride.getDtStopFrom(), ride.getTimeStopFromTarget(), 
							ride.getTimeStopFromReal(), ride.getStopIdAfter(), ride.getStopCodeAfter(), 
							ride.getDtStopAfter(), ride.getTimeStopAfterTarget(), ride.getTimeStopAfterReal(), 
							ride.getBreakpointIdFrom(), breakPointFrom.getLatitude(), 
							breakPointFrom.getLongitude(), breakPointFrom.getStopShortCode(), 
							breakPointFrom.getStationDescription(), ride.getBreakpointIdAfter(), 
							breakPointAfter.getLatitude(), breakPointAfter.getLongitude(), 
							breakPointAfter.getStopShortCode(), breakPointAfter.getStationDescription());
			
				BigDecimal latFrom = (rideBreakPointStop.getFromLatitude() != null && !rideBreakPointStop.getFromLatitude().equals("")) ?
				new BigDecimal(rideBreakPointStop.getFromLatitude()) : new BigDecimal(0);
				BigDecimal longFrom = (rideBreakPointStop.getFromLongitude() != null && !rideBreakPointStop.getFromLongitude().equals("")) ?
					new BigDecimal(rideBreakPointStop.getFromLongitude()) : new BigDecimal(0);
				List<BigDecimal> cFrom = Arrays.asList(new BigDecimal[]{latFrom, longFrom});
					
				BigDecimal latAfter = (rideBreakPointStop.getAfterLatitude() != null && !rideBreakPointStop.getAfterLatitude().equals("")) ?
					new BigDecimal(rideBreakPointStop.getAfterLatitude()) : new BigDecimal(0);
				BigDecimal longAfter = (rideBreakPointStop.getAfterLongitude() != null && !rideBreakPointStop.getAfterLongitude().equals("")) ?
					new BigDecimal(rideBreakPointStop.getAfterLongitude()) : new BigDecimal(0);
				List<BigDecimal> cTo = Arrays.asList(new BigDecimal[]{latAfter, longAfter});
				
				List<List<BigDecimal>> geoCoordinates = new ArrayList<List<BigDecimal>>();
				geoCoordinates.add(cFrom);
				geoCoordinates.add(cTo);
				
				GeoJsonMultiple geoJson = new GeoJsonMultiple();
				geoJson.setGeometry("LineString", geoCoordinates);
				
				rideBreakPointStop.setGeoJson(geoJson);
				c.output(rideBreakPointStop.toCsv()); 
			}
		}).withSideInputs(beakPointStopKvView));
		
		//Write CSV File
		rideDenormalized.apply("Write CSV File", TextIO.write().to("denormalizedTable.csv").withoutSharding());

		p.run().waitUntilFinish();

	}

}


