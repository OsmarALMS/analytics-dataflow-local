package com.opendata.zurich;

import java.util.Arrays;
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

import com.opendata.zurich.model.BreakPointStop;
import com.opendata.zurich.model.Ride;
import com.opendata.zurich.model.RideBreakPointStop;

/**
 * Process to create a Denormalized table from Zurich Travel Dalyes (Ride - BreakPointStop)
 * https://data.stadt-zuerich.ch/dataset/vbz_fahrzeiten_ogd
 * 
 * @author Osmar
 * 
 */
public class DenormalizedProcessRide {

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
						.from("D:\\Poc_Analytics\\_LOCAL\\breakpoint_stop.csv"));

		//Delays - Transform Cleanse Object KV >FROM<
		PCollection<KV<Long, Ride>> rideFromKv = rides.apply("ParDo Rides Cleanse|Object|KV From", ParDo.of(new DoFn<String, KV<Long, Ride>>() {
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
					c.output(KV.of(ride.getBreakpointIdFrom(), ride));
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
							List<String> lineContent = Arrays.asList(c.element().split(","));
							BreakPointStop breakPointStop = 
									new BreakPointStop(Long.parseLong(lineContent.get(0)), 
											lineContent.get(1), lineContent.get(2), 
											lineContent.get(3), lineContent.get(4));
							c.output(KV.of(breakPointStop.getBreakpointId(), breakPointStop));
						}
					}
				}));
		
		//JOINS RIDE AND BREAKPOINTSTOP-FROM >> START <<
		final TupleTag<Ride> rideFromTag = new TupleTag<Ride>();
		final TupleTag<BreakPointStop> breakPointStopFromTag = new TupleTag<BreakPointStop>();

		//Merge collection values into a CoGbkResult collection (Ride <- BreakPointStopFrom).
		PCollection<KV<Long, CoGbkResult>> joinedCollectionRideFrom =
				KeyedPCollectionTuple.of(rideFromTag, rideFromKv)
				.and(breakPointStopFromTag, beakPointStopKv)
				.apply(CoGroupByKey.create());

		//Joined Lines to Object (Ride <- BreakpointStop-From)
		PCollection<KV<Long, RideBreakPointStop>> rideAfterKv = 
				joinedCollectionRideFrom.apply("ParDo Ride/BreakPointsStops-From to Object", 
						ParDo.of(new DoFn<KV<Long, CoGbkResult>, KV<Long, RideBreakPointStop>>() {
							private static final long serialVersionUID = 1L;
							@ProcessElement
							public void processElement(ProcessContext c) {
								KV<Long, CoGbkResult> e = c.element();
								if(Lists.newArrayList(e.getValue().getAll(rideFromTag)).size() > 0) {
									for(Ride ride : Lists.newArrayList(e.getValue().getAll(rideFromTag))) {
										if(Lists.newArrayList(e.getValue().getAll(breakPointStopFromTag)).size() > 0) {
											BreakPointStop breakPointStopFrom = Lists.newArrayList(e.getValue().getAll(breakPointStopFromTag)).get(0);
											RideBreakPointStop rideBreakPointStop = 
													new RideBreakPointStop(ride.getOperationDate(), ride.getVehicleNumber(),
															ride.getCourseNumber(), ride.getSequenceStop(), ride.getStopIdFrom(), 
															ride.getStopCodeFrom(), ride.getDtStopFrom(), ride.getTimeStopFromTarget(), 
															ride.getTimeStopFromReal(), ride.getStopIdAfter(), ride.getStopCodeAfter(), 
															ride.getDtStopAfter(), ride.getTimeStopAfterTarget(), ride.getTimeStopAfterReal(), 
															ride.getRideId(), ride.getBreakpointIdFrom(), breakPointStopFrom.getLatitude(), 
															breakPointStopFrom.getLongitude(), breakPointStopFrom.getStopShortCode(), 
															breakPointStopFrom.getStationDescription(), ride.getBreakpointIdAfter(), "", "", "", "");
											c.output(KV.of(ride.getBreakpointIdAfter(), rideBreakPointStop));
										}else {
											RideBreakPointStop rideBreakPointStop = 
													new RideBreakPointStop(ride.getOperationDate(), ride.getVehicleNumber(),
															ride.getCourseNumber(), ride.getSequenceStop(), ride.getStopIdFrom(), 
															ride.getStopCodeFrom(), ride.getDtStopFrom(), ride.getTimeStopFromTarget(), 
															ride.getTimeStopFromReal(), ride.getStopIdAfter(), ride.getStopCodeAfter(), 
															ride.getDtStopAfter(), ride.getTimeStopAfterTarget(), ride.getTimeStopAfterReal(), 
															ride.getRideId(), ride.getBreakpointIdFrom(), "", "", "", "", 
															ride.getBreakpointIdAfter(), "", "", "", "");
											c.output(KV.of(ride.getBreakpointIdAfter(), rideBreakPointStop));
										}
									}
								}
							}
						}));
		//JOINS RIDE AND BREAKPOINTSTOP-FROM >> END <<
		
		//JOINS RIDE AND BREAKPOINTSTOP-AFTER >> START <<
		final TupleTag<RideBreakPointStop> rideAfterTag = new TupleTag<RideBreakPointStop>();
		final TupleTag<BreakPointStop> breakPointStopAfterTag = new TupleTag<BreakPointStop>();

		//Merge collection values into a CoGbkResult collection (Ride <- BreakPointStopAfter).
		PCollection<KV<Long, CoGbkResult>> joinedCollectionRideAfter =
				KeyedPCollectionTuple.of(rideAfterTag, rideAfterKv)
				.and(breakPointStopAfterTag, beakPointStopKv)
				.apply(CoGroupByKey.create());

		//Joined Lines to Object (Ride <- BreakpointStop-After)
		PCollection<String> rideDenormalized = 
				joinedCollectionRideAfter.apply("ParDo Ride/BreakPointsStops-After to Object", 
						ParDo.of(new DoFn<KV<Long, CoGbkResult>, String>() {
							private static final long serialVersionUID = 1L;
							@ProcessElement
							public void processElement(ProcessContext c) {
								KV<Long, CoGbkResult> e = c.element();
								if(Lists.newArrayList(e.getValue().getAll(rideAfterTag)).size() > 0) {
									for(RideBreakPointStop ride : Lists.newArrayList(e.getValue().getAll(rideAfterTag))) {
										if(Lists.newArrayList(e.getValue().getAll(breakPointStopAfterTag)).size() > 0) {
											BreakPointStop breakPointStopAfter = Lists.newArrayList(e.getValue().getAll(breakPointStopAfterTag)).get(0);
											RideBreakPointStop rideBreakPointStop = 
													new RideBreakPointStop(ride.getOperationDate(), ride.getVehicleNumber(),
															ride.getCourseNumber(), ride.getSequenceStop(), ride.getStopIdFrom(), 
															ride.getStopCodeFrom(), ride.getDtStopFrom(), ride.getTimeStopFromTarget(), 
															ride.getTimeStopFromReal(), ride.getStopIdAfter(), ride.getStopCodeAfter(), 
															ride.getDtStopAfter(), ride.getTimeStopAfterTarget(), ride.getTimeStopAfterReal(), 
															ride.getRideId(), ride.getBreakpointIdFrom(), ride.getFromLatitude(),
															ride.getFromLongitude(), ride.getFromStopShortCode(), 
															ride.getFromStationDescription(), breakPointStopAfter.getBreakpointId(), 
															breakPointStopAfter.getLatitude(), breakPointStopAfter.getLongitude(), 
															breakPointStopAfter.getStopShortCode(), breakPointStopAfter.getStationDescription());
											c.output(rideBreakPointStop.toCsv());
										}else {
											RideBreakPointStop rideBreakPointStop = 
													new RideBreakPointStop(ride.getOperationDate(), ride.getVehicleNumber(),
															ride.getCourseNumber(), ride.getSequenceStop(), ride.getStopIdFrom(), 
															ride.getStopCodeFrom(), ride.getDtStopFrom(), ride.getTimeStopFromTarget(), 
															ride.getTimeStopFromReal(), ride.getStopIdAfter(), ride.getStopCodeAfter(), 
															ride.getDtStopAfter(), ride.getTimeStopAfterTarget(), ride.getTimeStopAfterReal(), 
															ride.getRideId(), ride.getBreakpointIdFrom(), ride.getFromLatitude(),
															ride.getFromLongitude(), ride.getFromStopShortCode(), 
															ride.getFromStationDescription(), ride.getBreakpointIdAfter(), 
															"", "", "", "");
											c.output(rideBreakPointStop.toCsv());
										}
									}
								}
							}
						}));
		//JOINS RIDE AND BREAKPOINTSTOP-AFTER >> END <<
		
		//Write CSV File
		rideDenormalized.apply("Write CSV File", TextIO.write().to("denormalizedTable.csv").withoutSharding());

		p.run().waitUntilFinish();

	}

}


