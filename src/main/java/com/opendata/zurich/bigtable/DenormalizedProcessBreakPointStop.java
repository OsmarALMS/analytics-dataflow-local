package com.opendata.zurich.bigtable;

import java.util.Arrays;
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
import com.opendata.zurich.model.BreakPoint;
import com.opendata.zurich.model.Stop;
import com.opendata.zurich.options.BigTableEventsOptions;

/**
 * POC Analytics
 * 
 * Process to create a Denormalized table from Zurich Travel Dalyes (BreakPoint - Stop)
 * https://data.stadt-zuerich.ch/dataset/vbz_fahrzeiten_ogd
 * 
 * @author Osmar.Silva
 * 
 */
public class DenormalizedProcessBreakPointStop {

	static void runTypeModel(BigTableEventsOptions options) {

		Pipeline p = Pipeline.create(options);

		//BreakPoint - Read CSV Files
		PCollection<String> breakPoint = 
				p.apply("Read BreakPoints file", TextIO.read()
						.from(options.getInputBreakPoinstFile()));

		//Stops - Read CSV Files
		PCollection<String> stops = 
				p.apply("Read Stops file", TextIO.read()
						.from(options.getInputStopsFile()));

		//BreakPoint - Transform Cleanse Object KV
		PCollection<KV<Long, BreakPoint>> leftBeakPointStopKv = breakPoint.apply("ParDo BreakPoint Cleanse|Object|KV", 
				ParDo.of(new DoFn<String, KV<Long, BreakPoint>>() {
					private static final long serialVersionUID = 1L;
					@ProcessElement
					public void processElement(ProcessContext c) { 
						if(!c.element().trim().isEmpty() && !c.element().contains("halt_punkt_id")) {
							List<String> lineContent = Arrays.asList(c.element().split(","));
							BreakPoint breakPoint = new BreakPoint(
									Long.parseLong(lineContent.get(0)), Long.parseLong(lineContent.get(2)), 
									lineContent.get(3), lineContent.get(4));
							c.output(KV.of(breakPoint.getStopId(), breakPoint));
						}
					}
				}));

		//Stop - Transform Cleanse Object KV
		PCollection<KV<Long, Stop>> rightBeakPointStopKv = stops.apply("ParDo Stop Cleanse|Object|KV", 
				ParDo.of(new DoFn<String, KV<Long, Stop>>() {
					private static final long serialVersionUID = 1L;
					@ProcessElement
					public void processElement(ProcessContext c) { 
						if(!c.element().trim().isEmpty() && !c.element().contains("halt_id")) {
							if(c.element().contains("\"")) {
								List<String> lineContent1 = Arrays.asList(c.element().split("\""));
								List<String> lineContent2 = Arrays.asList(lineContent1.get(0).split(","));
								Stop stop = new Stop(Long.parseLong(lineContent2.get(0)), lineContent2.get(2), lineContent1.get(1));
								c.output(KV.of(stop.getStopId(), stop));
							}else {
								List<String> lineContent = Arrays.asList(c.element().split(","));
								Stop stop = new Stop(Long.parseLong(lineContent.get(0)), lineContent.get(2), lineContent.get(3));
								c.output(KV.of(stop.getStopId(), stop));
							}
						}
					}
				}));

		//JOINS BREAKPONTS AND STOPS >> START <<
		final TupleTag<BreakPoint> breakPointTag = new TupleTag<BreakPoint>();
		final TupleTag<Stop> stopTag = new TupleTag<Stop>();

		//Merge collection values into a CoGbkResult collection (Break Point <- Stop).
		PCollection<KV<Long, CoGbkResult>> joinedCollectionBS =
				KeyedPCollectionTuple.of(breakPointTag, leftBeakPointStopKv)
				.and(stopTag, rightBeakPointStopKv)
				.apply(CoGroupByKey.create());

		//Joined Lines to CSV (Break Point <- Stop)
		PCollection<TableRow> breakPointsStopDenormalizedRow = 
				joinedCollectionBS.apply("ParDo BreakPoints/Stops to Object|KV", ParDo.of(new DoFn<KV<Long, CoGbkResult>, TableRow>() {
					private static final long serialVersionUID = 1L;
					@ProcessElement
					public void processElement(ProcessContext c) {
						KV<Long, CoGbkResult> e = c.element();
						if(Lists.newArrayList(e.getValue().getAll(breakPointTag)).size() > 0) {
							for(BreakPoint breakPoint : Lists.newArrayList(e.getValue().getAll(breakPointTag))) {
								Stop stop = Lists.newArrayList(e.getValue().getAll(stopTag)).get(0);
								c.output(new TableRow()
										.set("breakpointId", breakPoint.getBreakpointId())
										.set("latitude", breakPoint.getLatitude())
										.set("longitude", breakPoint.getLongitude())
										.set("stopShortCode", stop.getStopShortCode())
										.set("stationDescription", stop.getStationDescription()));
							}
						}
					}
				}));
		//JOINS BREAKPONTS AND STOPS >> END <<
		
		PCollection<KV<ByteString, Iterable<Mutation>>> breakPointsStopDenormalizedMutation =
				breakPointsStopDenormalizedRow.apply("ParDo TableRow to Mutation", ParDo.of(new DoFn<TableRow, KV<ByteString, Iterable<Mutation>>>() {
					private static final long serialVersionUID = 1L;
					@ProcessElement
					public void processElement(ProcessContext c) {
						c.output(Utils.makeWrite(c.element().get("breakpointId").toString(), c.element(), null));
					}
				}));

		breakPointsStopDenormalizedMutation.apply("Write TO BigTable",
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


