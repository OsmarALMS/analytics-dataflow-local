package com.opendata.zurich.model;

import java.io.Serializable;

import com.google.gson.Gson;

public class FullRide implements Serializable {
	private static final long serialVersionUID = 1L;

	public FullRide(Long rideId, Long vehicleNumber, Long courseNumber, Long qtdeStops, String dtFirstStop,
			Long timeFirstStopReal, String dtLastStop, Long timeLastStopReal, Long fullTimeSec) {
		super();
		this.rideId = rideId;
		this.vehicleNumber = vehicleNumber;
		this.courseNumber = courseNumber;
		this.qtdeStops = qtdeStops;
		this.dtFirstStop = dtFirstStop;
		this.timeFirstStopReal = timeFirstStopReal;
		this.dtLastStop = dtLastStop;
		this.timeLastStopReal = timeLastStopReal;
		this.fullTimeSec = fullTimeSec;
	}

	private Long rideId;
	private Long vehicleNumber;
	private Long courseNumber;
	private Long qtdeStops;
	private String dtFirstStop;
	private Long timeFirstStopReal;
	private String dtLastStop;
	private Long timeLastStopReal;
	private Long fullTimeSec;
	private GeoJsonMultiple geoJson;

	public Long getRideId() {
		return rideId;
	}
	public void setRideId(Long rideId) {
		this.rideId = rideId;
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
	public Long getQtdeStops() {
		return qtdeStops;
	}
	public void setQtdeStops(Long qtdeStops) {
		this.qtdeStops = qtdeStops;
	}
	public String getDtFirstStop() {
		return dtFirstStop;
	}
	public void setDtFirstStop(String dtFirstStop) {
		this.dtFirstStop = dtFirstStop;
	}
	public Long getTimeFirstStopReal() {
		return timeFirstStopReal;
	}
	public void setTimeFirstStopReal(Long timeFirstStopReal) {
		this.timeFirstStopReal = timeFirstStopReal;
	}
	public String getDtLastStop() {
		return dtLastStop;
	}
	public void setDtLastStop(String dtLastStop) {
		this.dtLastStop = dtLastStop;
	}
	public Long getTimeLastStopReal() {
		return timeLastStopReal;
	}
	public void setTimeLastStopReal(Long timeLastStopReal) {
		this.timeLastStopReal = timeLastStopReal;
	}
	public GeoJsonMultiple getGeoJson() {
		return geoJson;
	}
	public void setGeoJson(GeoJsonMultiple geoJson) {
		this.geoJson = geoJson;
	}
	public Long getFullTimeSec() {
		return fullTimeSec;
	}
	public void setFullTimeSec(Long fullTimeSec) {
		this.fullTimeSec = fullTimeSec;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((courseNumber == null) ? 0 : courseNumber.hashCode());
		result = prime * result + ((dtFirstStop == null) ? 0 : dtFirstStop.hashCode());
		result = prime * result + ((dtLastStop == null) ? 0 : dtLastStop.hashCode());
		result = prime * result + ((qtdeStops == null) ? 0 : qtdeStops.hashCode());
		result = prime * result + ((rideId == null) ? 0 : rideId.hashCode());
		result = prime * result + ((timeFirstStopReal == null) ? 0 : timeFirstStopReal.hashCode());
		result = prime * result + ((timeLastStopReal == null) ? 0 : timeLastStopReal.hashCode());
		result = prime * result + ((vehicleNumber == null) ? 0 : vehicleNumber.hashCode());
		result = prime * result + ((fullTimeSec == null) ? 0 : fullTimeSec.hashCode());
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
		FullRide other = (FullRide) obj;
		if (courseNumber == null) {
			if (other.courseNumber != null)
				return false;
		} else if (!courseNumber.equals(other.courseNumber))
			return false;
		if (dtFirstStop == null) {
			if (other.dtFirstStop != null)
				return false;
		} else if (!dtFirstStop.equals(other.dtFirstStop))
			return false;
		if (dtLastStop == null) {
			if (other.dtLastStop != null)
				return false;
		} else if (!dtLastStop.equals(other.dtLastStop))
			return false;
		if (qtdeStops == null) {
			if (other.qtdeStops != null)
				return false;
		} else if (!qtdeStops.equals(other.qtdeStops))
			return false;
		if (rideId == null) {
			if (other.rideId != null)
				return false;
		} else if (!rideId.equals(other.rideId))
			return false;
		if (timeFirstStopReal == null) {
			if (other.timeFirstStopReal != null)
				return false;
		} else if (!timeFirstStopReal.equals(other.timeFirstStopReal))
			return false;
		if (timeLastStopReal == null) {
			if (other.timeLastStopReal != null)
				return false;
		} else if (!timeLastStopReal.equals(other.timeLastStopReal))
			return false;
		if (vehicleNumber == null) {
			if (other.vehicleNumber != null)
				return false;
		} else if (!vehicleNumber.equals(other.vehicleNumber))
			return false;
		if (fullTimeSec == null) {
			if (other.fullTimeSec != null)
				return false;
		} else if (!fullTimeSec.equals(other.fullTimeSec))
			return false;
		return true;
	}

	public String toCsv() {
		return this.rideId + ";" +
				this.vehicleNumber + ";" +
				this.courseNumber + ";" +
				this.qtdeStops + ";" +
				this.dtFirstStop + ";" +
				this.timeFirstStopReal + ";" +
				this.dtLastStop + ";" +
				this.timeLastStopReal + ";" +
				this.fullTimeSec + ";" +
				new Gson().toJson(this.geoJson);
	}

}
