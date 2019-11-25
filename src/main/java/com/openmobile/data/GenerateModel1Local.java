package com.openmobile.data;

import java.util.stream.Collectors;
import org.apache.beam.runners.direct.DirectRunner;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.extensions.sql.SqlTransform;
import org.apache.beam.sdk.io.Compression;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.schemas.Schema;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.Row;
import com.google.gson.Gson;
import com.openmobile.data.model.OpenMobile;
import com.openmobile.data.model.OpenMobileCSV;

public class GenerateModel1Local {

	public static void main(String[] args) {

		//Quantidade de Eventos por Ano/Device/Type
		//ano, device_info_manufacturer, device_info_model, type, sum(*), group by (ano, device_info_manufacturer, device_info_model, type)

		PipelineOptions options = PipelineOptionsFactory.create();
		options.setRunner(DirectRunner.class);
		Pipeline p = Pipeline.create(options);

		//Read Json Files
		PCollection<String> events = 
				p.apply("Read JsonFiles", TextIO.read()
						.from("D:\\NB25857\\Desktop\\POC_ANALITYCS\\*2017-01-01*.zip")
						.withCompression(Compression.ZIP));


		// Convert JsonArray File to individual Jsons.
		PCollection<OpenMobile> jsonEvents = events.apply("ParDo JsonArray to Object", ParDo.of(new DoFn<String, OpenMobile>() {
			private static final long serialVersionUID = 1L;
			@ProcessElement
			public void processElement(@Element String element, OutputReceiver<OpenMobile> receiver) {
				OpenMobile[] openMobileArray = new Gson().fromJson(element, OpenMobile[].class);
				for (OpenMobile openMobile : openMobileArray) {
					if(openMobile.getParameters() != null && openMobile.getParameters().getStart_time() != null) {
						receiver.output(openMobile);
					}
				}
			}
		}));

		//Transform Array to List Objectr CSV
		PCollection<OpenMobileCSV> eventsListCsv = jsonEvents.apply("ParDo CSV Obj", ParDo.of(new DoFn<OpenMobile, OpenMobileCSV>() {
			private static final long serialVersionUID = 1L;
			@ProcessElement
			public void processElement(ProcessContext c) { 
				String year = c.element().getParameters().getStart_time().split("T")[0].split("-")[0];
				c.output(new OpenMobileCSV(
						c.element().getId(), c.element().getParameters().getStart_time(), year, c.element().getType(), 
						c.element().getDevice_properties().getDevice_info().getModel(), 
						c.element().getDevice_properties().getDevice_info().getManufacturer(), 
						c.element().getDevice_properties().getLocation().getLatitude(), 
						c.element().getDevice_properties().getLocation().getLongitude(), 
						c.element().getDevice_properties().getCountry_code(), 
						c.element().getValues().getPackets_sent(), 
						c.element().getValues().getPacket_loss()));
			}
		}));
		final Schema SCHEMA = Schema.builder()
				.addStringField("ano")
				.addStringField("device_info_manufacturer")
				.addStringField("device_info_model")
				.addStringField("type").build();

		//Create a PCollection of Row
		PCollection<Row> rowCollection = eventsListCsv.apply("Transform to ROW Type", ParDo.of(new DoFn<OpenMobileCSV, Row>() {
			private static final long serialVersionUID = 1L;
			@ProcessElement
			public void processElement(ProcessContext c) {
				Row appRow = Row
						.withSchema(SCHEMA)
						.addValues(c.element().getYear(), c.element().getDevice_info_manufacturer(), 
								   c.element().getDevice_info_model(), c.element().getType())
						.build();
				c.output(appRow);
			}
		})).setRowSchema(SCHEMA);

		//Transform SQL
		PCollection<Row> rowSqlTransform = rowCollection.apply("Transform SQL", 
				SqlTransform.query(
						"SELECT ano, device_info_manufacturer, device_info_model, type, count(1) qtde " +
						"  FROM PCOLLECTION " +
						" GROUP BY ano, device_info_manufacturer, device_info_model, type")
				);

		//Transform SQL Row to String
		PCollection<String> rowFinalString = rowSqlTransform.apply("Transform SQL ROW to String", ParDo.of(new DoFn<Row, String>() {
			private static final long serialVersionUID = 1L;
			@ProcessElement
			public void processElement(ProcessContext c) {
				String line = c.element().getValues()
						.stream()
						.map(Object::toString)
						.collect(Collectors.joining(";"));
				c.output(line);
			}
		}));
		
		//Write CSV File
		rowFinalString.apply("Write CSV File", TextIO.write().to("output").withoutSharding());

		p.run().waitUntilFinish();

	}

}


