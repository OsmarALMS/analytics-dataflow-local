package com.openmobile.data.model;

import java.io.Serializable;

public class OpenMobileCSV implements Serializable{
	private static final long serialVersionUID = 1L;

	public OpenMobileCSV(String id, String start_time, String year, String type, String device_info_model,
			String device_info_manufacturer, String device_location_lat, String device_location_lon,
			String device_properties_country_code, String values_packets_sent, String values_packet_loss) {
		super();
		this.id = id;
		this.start_time = start_time;
		this.year = year;
		this.type = type;
		this.device_info_model = device_info_model;
		this.device_info_manufacturer = device_info_manufacturer;
		this.device_location_lat = device_location_lat;
		this.device_location_lon = device_location_lon;
		this.device_properties_country_code = device_properties_country_code;
		this.values_packets_sent = values_packets_sent;
		this.values_packet_loss = values_packet_loss;
	}
	
	private String id;
	private String start_time;
	private String year;
	private String type;
	private String device_info_model;
	private String device_info_manufacturer;
	private String device_location_lat;
	private String device_location_lon;
	private String device_properties_country_code;
	private String values_packets_sent;
	private String values_packet_loss;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDevice_info_model() {
		return device_info_model;
	}
	public void setDevice_info_model(String device_info_model) {
		this.device_info_model = device_info_model;
	}
	public String getDevice_info_manufacturer() {
		return device_info_manufacturer;
	}
	public void setDevice_info_manufacturer(String device_info_manufacturer) {
		this.device_info_manufacturer = device_info_manufacturer;
	}
	public String getDevice_location_lat() {
		return device_location_lat;
	}
	public void setDevice_location_lat(String device_location_lat) {
		this.device_location_lat = device_location_lat;
	}
	public String getDevice_location_lon() {
		return device_location_lon;
	}
	public void setDevice_location_lon(String device_location_lon) {
		this.device_location_lon = device_location_lon;
	}
	public String getDevice_properties_country_code() {
		return device_properties_country_code;
	}
	public void setDevice_properties_country_code(String device_properties_country_code) {
		this.device_properties_country_code = device_properties_country_code;
	}
	public String getValues_packets_sent() {
		return values_packets_sent;
	}
	public void setValues_packets_sent(String values_packets_sent) {
		this.values_packets_sent = values_packets_sent;
	}
	public String getValues_packet_loss() {
		return values_packet_loss;
	}
	public void setValues_packet_loss(String values_packet_loss) {
		this.values_packet_loss = values_packet_loss;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
}
