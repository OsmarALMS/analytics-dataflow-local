package com.openmobile.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.Compression;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.join.CoGbkResult;
import org.apache.beam.sdk.transforms.join.CoGroupByKey;
import org.apache.beam.sdk.transforms.join.KeyedPCollectionTuple;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TupleTag;
import org.apache.beam.vendor.guava.v20_0.com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.openmobile.data.model.OpenMobile;
import com.openmobile.data.model.OpenMobileUserCSV;
import com.openmobile.data.model.UserModel;

public class OpenMobileUserModel {

	static PCollection<OpenMobileUserCSV> getOpenMobileUserCSV(Pipeline p){
		
		//EVENTS - Read Json Files
		PCollection<String> events = 
				p.apply("Read JsonFiles", TextIO.read()
						.from("D:\\NB25857\\Desktop\\POC_ANALITYCS\\*2017-01-01*.zip")
						.withCompression(Compression.ZIP));

		//USERS - Read Json Files
		PCollection<String> users = 
				p.apply("Read UserFiles", TextIO.read()
						.from("D:\\NB25857\\Desktop\\POC_ANALITYCS\\user-model*.bz2")
						.withCompression(Compression.BZIP2));

		//EVENTS - Convert JsonArray File to individual Jsons.
		PCollection<OpenMobile> jsonEvents = events.apply("ParDo JsonArray to Object", ParDo.of(new DoFn<String, OpenMobile>() {
			private static final long serialVersionUID = 1L;
			@ProcessElement
			public void processElement(@Element String element, OutputReceiver<OpenMobile> receiver) {
				OpenMobile[] openMobileArray = new Gson().fromJson(element, OpenMobile[].class);
				for (OpenMobile openMobile : openMobileArray) {
					//Validade required fields
					if((openMobile.getParameters() != null && openMobile.getParameters().getStart_time() != null) &&
							(openMobile.getDevice_properties() != null && openMobile.getDevice_properties().getDevice_info() != null 
							&& openMobile.getDevice_properties().getDevice_info().getManufacturer() != null 
							&& openMobile.getDevice_properties().getDevice_info().getModel() != null)) {
						receiver.output(openMobile);
					}
				}
			}
		}));

		//USERS - Convert JsonArray File to individual Jsons.
		PCollection<UserModel> jsonUsers = users.apply("ParDo JsonArray to Object", ParDo.of(new DoFn<String, UserModel>() {
			private static final long serialVersionUID = 1L;
			@ProcessElement
			public void processElement(@Element String element, OutputReceiver<UserModel> receiver) {
				UserModel[] userArray = new Gson().fromJson(element, UserModel[].class);
				for (UserModel user : userArray) {
					receiver.output(user);
				}
			}
		}));

		//EVENTS - Transform KV Events
		PCollection<KV<String, String>> eventsKv = jsonEvents.apply("ParDo Events KV", ParDo.of(new DoFn<OpenMobile, KV<String, String>>() {
			private static final long serialVersionUID = 1L;
			@ProcessElement
			public void processElement(ProcessContext c) { 
				String year = c.element().getParameters().getStart_time().split("T")[0].split("-")[0];
				c.output(
						KV.of(c.element().getId(), 
								new ArrayList<String>( 
										Arrays.asList(
												c.element().getId(), c.element().getParameters().getStart_time(), year, c.element().getType(), 
												c.element().getDevice_properties().getDevice_info().getModel(), 
												c.element().getDevice_properties().getDevice_info().getManufacturer(), 
												c.element().getDevice_properties().getLocation().getLatitude(), 
												c.element().getDevice_properties().getLocation().getLongitude(), 
												c.element().getDevice_properties().getCountry_code(), 
												c.element().getValues().getPackets_sent(), 
												c.element().getValues().getPacket_loss()))
								.stream().collect(Collectors.joining(";"))));
			}
		}));

		//USERS - Transform KV Events
		PCollection<KV<String, String>> usersKv = jsonUsers.apply("ParDo Events KV", ParDo.of(new DoFn<UserModel, KV<String, String>>() {
			private static final long serialVersionUID = 1L;
			@ProcessElement
			public void processElement(ProcessContext c) { 
				c.output(
						KV.of(c.element().getId(), 
								new ArrayList<String>( 
										Arrays.asList(
												c.element().getId(), c.element().getName(), 
												c.element().getBirthday(), c.element().getAddress(),
												c.element().getCountry(), c.element().getCountrycode()))
								.stream().collect(Collectors.joining(";"))));
			}
		}));


		final TupleTag<String> eventsTag = new TupleTag<String>();
		final TupleTag<String> userTag = new TupleTag<String>();

		// Merge collection values into a CoGbkResult collection.
		PCollection<KV<String, CoGbkResult>> joinedCollection =
				KeyedPCollectionTuple.of(eventsTag, eventsKv)
				.and(userTag, usersKv)
				.apply(CoGroupByKey.<String>create());


		//Joined Lines to Object
		PCollection<OpenMobileUserCSV> joinedLinesCsv = 
				joinedCollection.apply("ParDo Joined Lines to Object", ParDo.of(new DoFn<KV<String, CoGbkResult>, OpenMobileUserCSV>() {
					private static final long serialVersionUID = 1L;
					@ProcessElement
					public void processElement(ProcessContext c) {
						KV<String, CoGbkResult> e = c.element();
						List<String> listEvent = Arrays.asList(Lists.newArrayList(e.getValue().getAll(eventsTag)).get(0).split(";"));
						List<String> listUser = Arrays.asList(Lists.newArrayList(e.getValue().getAll(userTag)).get(0).split(";"));
						
						OpenMobileUserCSV openMobileUserCsv = new OpenMobileUserCSV(
								listEvent.get(0), listEvent.get(1), listEvent.get(2), listEvent.get(3), 
								listEvent.get(4), listEvent.get(5), listEvent.get(6), listEvent.get(7), 
								listEvent.get(8), listEvent.get(9), listEvent.get(10), listUser.get(1), 
								listUser.get(2), listUser.get(3), listUser.get(4), listUser.get(5));

						c.output(openMobileUserCsv);
					}
				}));
		
		return joinedLinesCsv;
	}
}
