package com.openmobile.data.model;

import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class OpenMobileUserCSV implements Serializable{
	private static final long serialVersionUID = 1L;

	public OpenMobileUserCSV(String id, String start_time, String year, String type, String device_info_model,
			String device_info_manufacturer, String device_location_lat, String device_location_lon,
			String device_properties_country_code, String values_packets_sent, String values_packet_loss,
			String user_name, String user_birthday, String user_address, String user_country, String user_countrycode) {
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
		this.user_name = user_name;
		this.user_birthday = user_birthday;
		this.user_address = user_address;
		this.user_country = user_country;
		this.user_countrycode = user_countrycode;
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
	private String user_name;
	private String user_birthday;
	private String user_address;
	private String user_country;
	private String user_countrycode;
	
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
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_birthday() {
		return user_birthday;
	}
	public void setUser_birthday(String user_birthday) {
		this.user_birthday = user_birthday;
	}
	public String getUser_address() {
		return user_address;
	}
	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}
	public String getUser_country() {
		return user_country;
	}
	public void setUser_country(String user_country) {
		this.user_country = user_country;
	}
	public String getUser_countrycode() {
		return user_countrycode;
	}
	public void setUser_countrycode(String user_countrycode) {
		this.user_countrycode = user_countrycode;
	}
	public String getUser_age() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return String.valueOf(getDiffYears(sdf.parse(this.getUser_birthday()), new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static int getDiffYears(Date first, Date last) {
	    Calendar a = getCalendar(first);
	    Calendar b = getCalendar(last);
	    int diff = b.get(YEAR) - a.get(YEAR);
	    if (a.get(MONTH) > b.get(MONTH) || 
	        (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
	        diff--;
	    }
	    return diff;
	}

	public static Calendar getCalendar(Date date) {
	    Calendar cal = Calendar.getInstance(Locale.US);
	    cal.setTime(date);
	    return cal;
	}
}
