package com.opendata.zurich.model;

import java.util.List;

public class GeoJsonSingle {

	public GeoJsonSingle(String type, List<String> coordinates) {
		super();
		this.type = type;
		this.coordinates = coordinates;
	}
	
	private String type;
	private List<String> coordinates;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(List<String> coordinates) {
		this.coordinates = coordinates;
	}
	
}
