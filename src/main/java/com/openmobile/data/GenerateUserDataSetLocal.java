package com.openmobile.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import org.apache.beam.sdk.transforms.Flatten;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionList;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.openmobile.data.model.OpenMobile;
import com.openmobile.data.model.UserModel;

public class GenerateUserDataSetLocal {

	public static void main(String[] args) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
		PipelineOptions options = PipelineOptionsFactory.create();
		options.setRunner(DirectRunner.class);
		Pipeline p = Pipeline.create(options);
	
		//Read Json Files
		PCollection<String> files1 = 
			p.apply("Read JsonFiles1", TextIO.read()
				.from("D:\\NB25857\\Desktop\\POC_ANALITYCS\\*S-2017-01-01 0*.zip")
				.withCompression(Compression.ZIP));
		
		//Read Json Files2
		PCollection<String> files2 = 
			p.apply("Read JsonFiles2", TextIO.read()
				.from("D:\\NB25857\\Desktop\\POC_ANALITYCS\\*S-2017-01-01 1*.zip")
				.withCompression(Compression.ZIP));
		
		PCollectionList<String> pcs = PCollectionList.of(files1).and(files2);
		PCollection<String> files = pcs.apply("Flatten PCollections", Flatten.<String>pCollections());
		
		//Parse Json to Object
		PCollection<OpenMobile[]> objects = files.apply("Parse Json2Object", ParseJsons.of(OpenMobile[].class))
				.setCoder(SerializableCoder.of(OpenMobile[].class));
		
		//Create UserModel
		PCollection<String> filteredObjects = objects.apply("ParDo Create User Model", ParDo.of(new DoFn<OpenMobile[], String>() {
			private static final long serialVersionUID = 1L;
			@ProcessElement
	        public void processElement(ProcessContext c) { 
				List<UserModel> listUserModel = new ArrayList<UserModel>();
				Arrays.asList(c.element()).forEach(o -> {
					Faker faker = new Faker();
					listUserModel.add(new UserModel(o.getId(), faker.name().fullName(), sdf.format(faker.date().birthday()), 
							faker.address().streetAddress(), faker.address().country(), faker.address().countryCode()));
				});
				Gson gson = new GsonBuilder().create();
				String json = gson.toJson(listUserModel);
				c.output(json);
	        }
		}));
		
		//Write Json to File
		filteredObjects.apply("Write Model", TextIO.write().to("user-model").withCompression(Compression.BZIP2));
		
		p.run().waitUntilFinish();
		
	}
}


