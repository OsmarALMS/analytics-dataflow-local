package com.opendata.zurich.model;

import java.io.Serializable;

public class BreakPoint implements Serializable{
	private static final long serialVersionUID = 1L;

	public BreakPoint(Long breakpointId, Long stopId, String latitude, String longitude) {
		super();
		this.breakpointId = breakpointId;
		this.stopId = stopId;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	private Long breakpointId;
	private Long stopId;
	private String latitude;
	private String longitude;
	
	public Long getBreakpointId() {
		return breakpointId;
	}
	public void setBreakpointId(Long breakpointId) {
		this.breakpointId = breakpointId;
	}
	public Long getStopId() {
		return stopId;
	}
	public void setStopId(Long stopId) {
		this.stopId = stopId;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((breakpointId == null) ? 0 : breakpointId.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((stopId == null) ? 0 : stopId.hashCode());
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
		BreakPoint other = (BreakPoint) obj;
		if (breakpointId == null) {
			if (other.breakpointId != null)
				return false;
		} else if (!breakpointId.equals(other.breakpointId))
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
		if (stopId == null) {
			if (other.stopId != null)
				return false;
		} else if (!stopId.equals(other.stopId))
			return false;
		return true;
	}
}
