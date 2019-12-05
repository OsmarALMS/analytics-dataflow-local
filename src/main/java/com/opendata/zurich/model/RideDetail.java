package com.opendata.zurich.model;

import java.io.Serializable;

import com.google.api.services.bigquery.model.TableRow;
import com.google.gson.Gson;

public class RideDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	public RideDetail(Long rideId, String type, Long vehicleNumber, Long courseNumber, Long sequenceStop, Long stopId,
			String stopCode, String dtStop, Long timeStopReal, Long breakpointId, String latitude, String longitude,
			String stopShortCode, String stationDescription) {
		super();
		this.rideId = rideId;
		this.type = type;
		this.vehicleNumber = vehicleNumber;
		this.courseNumber = courseNumber;
		this.sequenceStop = sequenceStop;
		this.stopId = stopId;
		this.stopCode = stopCode;
		this.dtStop = dtStop;
		this.timeStopReal = timeStopReal;
		this.breakpointId = breakpointId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.stopShortCode = stopShortCode;
		this.stationDescription = stationDescription;
	}

	private Long rideId;
	private String type;
	private Long vehicleNumber;
	private Long courseNumber;
	private Long sequenceStop;
	private Long stopId;
	private String stopCode;
	private String dtStop;
	private Long timeStopReal;
	private Long breakpointId;
	private String latitude;
	private String longitude;
	private String stopShortCode;
	private String stationDescription;
	private GeoJsonSingle geoJson;

	public Long getRideId() {
		return rideId;
	}
	public void setRideId(Long rideId) {
		this.rideId = rideId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(Long vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public Long getCourseNumber() {
		return courseNumber;
	}
	public void setCourseNumber(Long courseNumber) {
		this.courseNumber = courseNumber;
	}
	public Long getSequenceStop() {
		return sequenceStop;
	}
	public void setSequenceStop(Long sequenceStop) {
		this.sequenceStop = sequenceStop;
	}
	public Long getStopId() {
		return stopId;
	}
	public void setStopId(Long stopId) {
		this.stopId = stopId;
	}
	public String getStopCode() {
		return stopCode;
	}
	public void setStopCode(String stopCode) {
		this.stopCode = stopCode;
	}
	public String getDtStop() {
		return dtStop;
	}
	public void setDtStop(String dtStop) {
		this.dtStop = dtStop;
	}
	public Long getTimeStopReal() {
		return timeStopReal;
	}
	public void setTimeStopReal(Long timeStopReal) {
		this.timeStopReal = timeStopReal;
	}
	public Long getBreakpointId() {
		return breakpointId;
	}
	public void setBreakpointId(Long breakpointId) {
		this.breakpointId = breakpointId;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getStopShortCode() {
		return stopShortCode;
	}
	public void setStopShortCode(String stopShortCode) {
		this.stopShortCode = stopShortCode;
	}
	public String getStationDescription() {
		return stationDescription;
	}
	public void setStationDescription(String stationDescription) {
		this.stationDescription = stationDescription;
	}
	public GeoJsonSingle getGeoJson() {
		return geoJson;
	}
	public void setGeoJson(GeoJsonSingle geoJson) {
		this.geoJson = geoJson;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((breakpointId == null) ? 0 : breakpointId.hashCode());
		result = prime * result + ((courseNumber == null) ? 0 : courseNumber.hashCode());
		result = prime * result + ((dtStop == null) ? 0 : dtStop.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((rideId == null) ? 0 : rideId.hashCode());
		result = prime * result + ((sequenceStop == null) ? 0 : sequenceStop.hashCode());
		result = prime * result + ((stationDescription == null) ? 0 : stationDescription.hashCode());
		result = prime * result + ((stopCode == null) ? 0 : stopCode.hashCode());
		result = prime * result + ((stopId == null) ? 0 : stopId.hashCode());
		result = prime * result + ((stopShortCode == null) ? 0 : stopShortCode.hashCode());
		result = prime * result + ((timeStopReal == null) ? 0 : timeStopReal.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((vehicleNumber == null) ? 0 : vehicleNumber.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RideDetail other = (RideDetail) obj;
		if (breakpointId == null) {
			if (other.breakpointId != null)
				return false;
		} else if (!breakpointId.equals(other.breakpointId))
			return false;
		if (courseNumber == null) {
			if (other.courseNumber != null)
				return false;
		} else if (!courseNumber.equals(other.courseNumber))
			return false;
		if (dtStop == null) {
			if (other.dtStop != null)
				return false;
		} else if (!dtStop.equals(other.dtStop))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (rideId == null) {
			if (other.rideId != null)
				return false;
		} else if (!rideId.equals(other.rideId))
			return false;
		if (sequenceStop == null) {
			if (other.sequenceStop != null)
				return false;
		} else if (!sequenceStop.equals(other.sequenceStop))
			return false;
		if (stationDescription == null) {
			if (other.stationDescription != null)
				return false;
		} else if (!stationDescription.equals(other.stationDescription))
			return false;
		if (stopCode == null) {
			if (other.stopCode != null)
				return false;
		} else if (!stopCode.equals(other.stopCode))
			return false;
		if (stopId == null) {
			if (other.stopId != null)
				return false;
		} else if (!stopId.equals(other.stopId))
			return false;
		if (stopShortCode == null) {
			if (other.stopShortCode != null)
				return false;
		} else if (!stopShortCode.equals(other.stopShortCode))
			return false;
		if (timeStopReal == null) {
			if (other.timeStopReal != null)
				return false;
		} else if (!timeStopReal.equals(other.timeStopReal))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (vehicleNumber == null) {
			if (other.vehicleNumber != null)
				return false;
		} else if (!vehicleNumber.equals(other.vehicleNumber))
			return false;
		return true;
	}

	public String toCsv() {
		return this.rideId + ";" +
				this.type + ";" +
				this.vehicleNumber + ";" +
				this.courseNumber + ";" +
				this.sequenceStop + ";" +
				this.stopId + ";" +
				this.stopCode + ";" +
				this.dtStop + ";" +
				this.timeStopReal + ";" +
				this.breakpointId + ";" +
				this.latitude + ";" +
				this.longitude + ";" +
				this.stopShortCode + ";" +
				this.stationDescription + ";" +
				new Gson().toJson(this.geoJson);
	}
	
	public TableRow toTableRow() {
		return new TableRow().set("rideId", this.rideId)
				.set("type", this.type)
				.set("vehicleNumber", this.vehicleNumber)
				.set("courseNumber", this.courseNumber)
				.set("sequenceStop", this.sequenceStop)
				.set("stopId", this.stopId)
				.set("stopCode", this.stopCode)
				.set("dtStop", this.dtStop)
				.set("timeStopReal", this.timeStopReal)
				.set("breakpointId", this.breakpointId)
				.set("latitude", this.latitude)
				.set("longitude", this.longitude)
				.set("stopShortCode", this.stopShortCode)
				.set("stationDescription", this.stationDescription)
				.set("geojson", new Gson().toJson(this.geoJson));
	}
	
}
