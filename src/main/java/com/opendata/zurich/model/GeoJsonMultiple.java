package com.opendata.zurich.model;

import java.util.List;

public class GeoJsonMultiple {

	public GeoJsonMultiple(String type, List<List<String>> coordinates) {
		super();
		this.type = type;
		this.coordinates = coordinates;
	}
	
	private String type;
	private List<List<String>> coordinates;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<List<String>> getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(List<List<String>> coordinates) {
		this.coordinates = coordinates;
	}
	
}
