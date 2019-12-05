package com.opendata.zurich.options;

import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.Validation.Required;

public interface BigQueryEventsOptions extends PipelineOptions {

	@Description("Path of the rides file to read from")
	String getInputRidesFile();
	void setInputRidesFile(String value);

	@Description("Path of the breakpoints file to read from")
	String getInputBreakPoinstFile();
	void setInputBreakPoinstFile(String value);
	
	@Description("Path of the stops file to read from")
	String getInputStopsFile();
	void setInputStopsFile(String value);
	
	@Description("Path of the breakpointStop desenormalized file to read from")
	String getInputBreakpointStopFile();
	void setInputBreakpointStopFile(String value);
	
	@Description("Path of the denormalized ride files to read from")
	String getInputDenormalizedRideFile();
	void setInputDenormalizedRideFile(String value);

	@Description("BigQuery DataSet")
	@Required
	String getBigQueryDataSet();
	void setBigQueryDataSet(String value);
	
	@Description("BigQuery Table")
	@Required
	String getBigQueryTable();
	void setBigQueryTable(String value);

}
