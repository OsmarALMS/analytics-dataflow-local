package com.opendata.zurich.model;

import java.io.Serializable;

public class Stop implements Serializable {
	private static final long serialVersionUID = 1L;

	public Stop(Long stopId, String stopShortCode, String stationDescription) {
		super();
		this.stopId = stopId;
		this.stopShortCode = stopShortCode;
		this.stationDescription = stationDescription;
	}
	
	private Long stopId;
	private String stopShortCode;
	private String stationDescription;
	
	public Long getStopId() {
		return stopId;
	}
	public void setStopId(Long stopId) {
		this.stopId = stopId;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stationDescription == null) ? 0 : stationDescription.hashCode());
		result = prime * result + ((stopId == null) ? 0 : stopId.hashCode());
		result = prime * result + ((stopShortCode == null) ? 0 : stopShortCode.hashCode());
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
		Stop other = (Stop) obj;
		if (stationDescription == null) {
			if (other.stationDescription != null)
				return false;
		} else if (!stationDescription.equals(other.stationDescription))
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
		return true;
	}
}
