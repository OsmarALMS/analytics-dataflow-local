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
import org.apache.beam.sdk.values.PCollection;

import com.opendata.zurich.model.GeoJsonSingle;
import com.opendata.zurich.model.RideDetail;

/**
 * Process to create a Detailed file from denormalized ride table
 * 
 * @author Osmar
 * 
 */
public class RideDetailProcess {

	public static void main(String[] args) {

		PipelineOptions options = PipelineOptionsFactory.create();
		options.setRunner(DirectRunner.class);
		Pipeline p = Pipeline.create(options);

		//DenormalizedRides - Read CSV Files
		PCollection<String> rides = 
				p.apply("Read DenormalizedRides files ", TextIO.read()
						.from("D:\\Poc_Analytics\\_LOCAL\\denormalizedTable.csv"));

		//Transform to Object > Strin CSV
		PCollection<String> rideDetail = rides.apply("ParDo to RideDetail CSV", ParDo.of(new DoFn<String, String>() {
			private static final long serialVersionUID = 1L;
			@ProcessElement
			public void processElement(@Element String element, OutputReceiver<String> receiver) {
				if(!element.trim().isEmpty()) {
					List<String> lineContent = Arrays.asList(element.split(";"));
					
					//From
					RideDetail rideDetailFrom = new RideDetail(
							Long.parseLong(lineContent.get(0)), 	//rideId
							"from", 								//type
							Long.parseLong(lineContent.get(2)), 	//vehicleNumber
							Long.parseLong(lineContent.get(3)), 	//courseNumber
							Long.parseLong(lineContent.get(4)), 	//sequenceStop
							Long.parseLong(lineContent.get(5)), 	//stopId-FROM
							lineContent.get(6), 					//stopCode-FROM
							lineContent.get(7), 					//dtStop-FROM
							Long.parseLong(lineContent.get(9)), 	//timeStopReal-FROM
							Long.parseLong(lineContent.get(15)), 	//breakpointId-FROM
							lineContent.get(16), 					//latitude-FROM
							lineContent.get(17), 					//longitude-FROM
							lineContent.get(18), 					//stopShortCode-FROM
							lineContent.get(19));					//stationDescription-FROM
					
					List<String> cFrom = Arrays.asList(new String[]{rideDetailFrom.getLatitude(), rideDetailFrom.getLongitude()});
					rideDetailFrom.setGeoJson(new GeoJsonSingle("Point", cFrom));
					receiver.output(rideDetailFrom.toCsv());
					
					//After
					RideDetail rideDetailAfter = new RideDetail(
							Long.parseLong(lineContent.get(0)), 	//rideId
							"after", 								//type
							Long.parseLong(lineContent.get(2)), 	//vehicleNumber
							Long.parseLong(lineContent.get(3)), 	//courseNumber
							Long.parseLong(lineContent.get(4)), 	//sequenceStop
							Long.parseLong(lineContent.get(10)), 	//stopId-FROM
							lineContent.get(11), 					//stopCode-AFTER
							lineContent.get(12), 					//dtStop-AFTER
							Long.parseLong(lineContent.get(14)), 	//timeStopReal-AFTER
							Long.parseLong(lineContent.get(20)), 	//breakpointId-AFTER
							lineContent.get(21), 					//latitude-AFTER
							lineContent.get(22), 					//longitude-AFTER
							lineContent.get(23), 					//stopShortCode-AFTER
							lineContent.get(24));					//stationDescription-AFTER
					
					List<String> cAfter = Arrays.asList(new String[]{rideDetailAfter.getLatitude(), rideDetailAfter.getLongitude()});
					rideDetailAfter.setGeoJson(new GeoJsonSingle("Point", cAfter));
					receiver.output(rideDetailAfter.toCsv());
				}
			}
		}));

		//Write CSV File
		rideDetail.apply("Write CSV File", TextIO.write().to("detailTable.csv").withoutSharding());

		p.run().waitUntilFinish();

	}

}


