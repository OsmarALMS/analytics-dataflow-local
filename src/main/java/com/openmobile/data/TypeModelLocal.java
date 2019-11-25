package com.openmobile.data;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.beam.runners.direct.DirectRunner;
//import org.apache.beam.runners.direct.DirectRunner;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.extensions.jackson.ParseJsons;
import org.apache.beam.sdk.io.Compression;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.openmobile.data.model.OpenMobile;

public class TypeModelLocal {

public static void main(String[] args) {
		
		PipelineOptions options = PipelineOptionsFactory.create();
		options.setRunner(DirectRunner.class);
		Pipeline p = Pipeline.create(options);
	
		//Read Json Files
		PCollection<String> files = 
			p.apply("Read JsonFiles", TextIO.read()
				.from("D:\\NB25857\\Desktop\\POC_ANALITYCS\\*2017-01-01*.zip")
				.withCompression(Compression.ZIP));
		
		//Parse Json to Object
		PCollection<OpenMobile[]> objects = files.apply("Parse Json2Object", ParseJsons.of(OpenMobile[].class))
				.setCoder(SerializableCoder.of(OpenMobile[].class));
		
		//Filter Type and Parse Object to Json
		PCollection<String> filteredObjects = objects.apply("ParDo Filter/Parse", ParDo.of(new DoFn<OpenMobile[], String>() {
			private static final long serialVersionUID = 1L;
			@ProcessElement
	        public void processElement(ProcessContext c) { 
				Gson gson = new GsonBuilder().create();
				String json = gson.toJson(Arrays.asList(c.element()).stream().filter(o -> o.getType().equals("dns_lookup")).collect(Collectors.toList()));
				c.output(json);
	        }
		}));
		
		//Write Json to File
		filteredObjects.apply("Write Model", TextIO.write().to("openmobile-types").withCompression(Compression.BZIP2));
		
		p.run().waitUntilFinish();
		
	}
}


