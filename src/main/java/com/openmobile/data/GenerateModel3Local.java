package com.openmobile.data;

import java.util.stream.Collectors;
import org.apache.beam.runners.direct.DirectRunner;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.extensions.sql.SqlTransform;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.schemas.Schema;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.Row;
import com.openmobile.data.model.OpenMobileUserCSV;

public class GenerateModel3Local {

	public static void main(String[] args) {

		//Number of Events per Year/Devices/UserAge
		//ano, device_info_manufacturer, device_info_model, user_age, sum(*), group by (ano, device_info_manufacturer, device_info_model, user_age)

		PipelineOptions options = PipelineOptionsFactory.create();
		options.setRunner(DirectRunner.class);
		Pipeline p = Pipeline.create(options);

		final Schema SCHEMA = Schema.builder()
				.addStringField("ano")
				.addStringField("device_info_manufacturer")
				.addStringField("device_info_model")
				.addStringField("user_age").build();

		//Create a PCollection of Row
		PCollection<Row> rowCollection = OpenMobileUserModel.getOpenMobileUserCSV(p)
				.apply("Transform to ROW Type", ParDo.of(new DoFn<OpenMobileUserCSV, Row>() {
					private static final long serialVersionUID = 1L;
					@ProcessElement
					public void processElement(ProcessContext c) {
						Row appRow = Row
								.withSchema(SCHEMA)
								.addValues(c.element().getYear(), c.element().getDevice_info_manufacturer(), 
										   c.element().getDevice_info_model(), c.element().getUser_age())
								.build();
						c.output(appRow);
					}
				})).setRowSchema(SCHEMA);

		//Transform SQL
		PCollection<Row> rowSqlTransform = rowCollection.apply("Transform SQL", 
				SqlTransform.query(
						"SELECT ano, device_info_manufacturer, device_info_model, user_age, count(1) qtde " +
						"  FROM PCOLLECTION " +
						" GROUP BY ano, device_info_manufacturer, device_info_model, user_age")
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


