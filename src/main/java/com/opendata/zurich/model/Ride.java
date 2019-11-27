package com.opendata.zurich.model;

import java.io.Serializable;

public class Ride implements Serializable {
	private static final long serialVersionUID = 1L;

	public Ride(String operationDate, Long vehicleNumber, Long courseNumber, Long sequenceStop,
			Long stopIdFrom, String stopCodeFrom, String dtStopFrom, Long timeStopFromTarget,
			Long timeStopFromReal, Long stopIdAfter, String stopCodeAfter, String dtStopAfter,
			Long timeStopAfterTarget, Long timeStopAfterReal, Long rideId, Long breakpointIdFrom,
			Long breakpointIdAfter) {
		super();
		this.operationDate = operationDate;
		this.vehicleNumber = vehicleNumber;
		this.courseNumber = courseNumber;
		this.sequenceStop = sequenceStop;
		this.stopIdFrom = stopIdFrom;
		this.stopCodeFrom = stopCodeFrom;
		this.dtStopFrom = dtStopFrom;
		this.timeStopFromTarget = timeStopFromTarget;
		this.timeStopFromReal = timeStopFromReal;
		this.stopIdAfter = stopIdAfter;
		this.stopCodeAfter = stopCodeAfter;
		this.dtStopAfter = dtStopAfter;
		this.timeStopAfterTarget = timeStopAfterTarget;
		this.timeStopAfterReal = timeStopAfterReal;
		this.rideId = rideId;
		this.breakpointIdFrom = breakpointIdFrom;
		this.breakpointIdAfter = breakpointIdAfter;
	}
	
	private String operationDate;
	private Long vehicleNumber;
	private Long courseNumber;
	private Long sequenceStop;
	private Long stopIdFrom;
	private String stopCodeFrom;
	private String dtStopFrom;
	private Long timeStopFromTarget;
	private Long timeStopFromReal;
	private Long stopIdAfter;
	private String stopCodeAfter;
	private String dtStopAfter;
	private Long timeStopAfterTarget;
	private Long timeStopAfterReal;
	private Long rideId;
	private Long breakpointIdFrom;
	private Long breakpointIdAfter;
	
	public String getOperationDate() {
		return operationDate;
	}
	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
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
	public Long getStopIdFrom() {
		return stopIdFrom;
	}
	public void setStopIdFrom(Long stopIdFrom) {
		this.stopIdFrom = stopIdFrom;
	}
	public String getStopCodeFrom() {
		return stopCodeFrom;
	}
	public void setStopCodeFrom(String stopCodeFrom) {
		this.stopCodeFrom = stopCodeFrom;
	}
	public String getDtStopFrom() {
		return dtStopFrom;
	}
	public void setDtStopFrom(String dtStopFrom) {
		this.dtStopFrom = dtStopFrom;
	}
	public Long getTimeStopFromTarget() {
		return timeStopFromTarget;
	}
	public void setTimeStopFromTarget(Long timeStopFromTarget) {
		this.timeStopFromTarget = timeStopFromTarget;
	}
	public Long getTimeStopFromReal() {
		return timeStopFromReal;
	}
	public void setTimeStopFromReal(Long timeStopFromReal) {
		this.timeStopFromReal = timeStopFromReal;
	}
	public Long getStopIdAfter() {
		return stopIdAfter;
	}
	public void setStopIdAfter(Long stopIdAfter) {
		this.stopIdAfter = stopIdAfter;
	}
	public String getStopCodeAfter() {
		return stopCodeAfter;
	}
	public void setStopCodeAfter(String stopCodeAfter) {
		this.stopCodeAfter = stopCodeAfter;
	}
	public String getDtStopAfter() {
		return dtStopAfter;
	}
	public void setDtStopAfter(String dtStopAfter) {
		this.dtStopAfter = dtStopAfter;
	}
	public Long getTimeStopAfterTarget() {
		return timeStopAfterTarget;
	}
	public void setTimeStopAfterTarget(Long timeStopAfterTarget) {
		this.timeStopAfterTarget = timeStopAfterTarget;
	}
	public Long getTimeStopAfterReal() {
		return timeStopAfterReal;
	}
	public void setTimeStopAfterReal(Long timeStopAfterReal) {
		this.timeStopAfterReal = timeStopAfterReal;
	}
	public Long getRideId() {
		return rideId;
	}
	public void setRideId(Long rideId) {
		this.rideId = rideId;
	}
	public Long getBreakpointIdFrom() {
		return breakpointIdFrom;
	}
	public void setBreakpointIdFrom(Long breakpointIdFrom) {
		this.breakpointIdFrom = breakpointIdFrom;
	}
	public Long getBreakpointIdAfter() {
		return breakpointIdAfter;
	}
	public void setBreakpointIdAfter(Long breakpointIdAfter) {
		this.breakpointIdAfter = breakpointIdAfter;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((breakpointIdAfter == null) ? 0 : breakpointIdAfter.hashCode());
		result = prime * result + ((breakpointIdFrom == null) ? 0 : breakpointIdFrom.hashCode());
		result = prime * result + ((courseNumber == null) ? 0 : courseNumber.hashCode());
		result = prime * result + ((dtStopAfter == null) ? 0 : dtStopAfter.hashCode());
		result = prime * result + ((dtStopFrom == null) ? 0 : dtStopFrom.hashCode());
		result = prime * result + ((operationDate == null) ? 0 : operationDate.hashCode());
		result = prime * result + ((rideId == null) ? 0 : rideId.hashCode());
		result = prime * result + ((sequenceStop == null) ? 0 : sequenceStop.hashCode());
		result = prime * result + ((stopCodeAfter == null) ? 0 : stopCodeAfter.hashCode());
		result = prime * result + ((stopCodeFrom == null) ? 0 : stopCodeFrom.hashCode());
		result = prime * result + ((stopIdAfter == null) ? 0 : stopIdAfter.hashCode());
		result = prime * result + ((stopIdFrom == null) ? 0 : stopIdFrom.hashCode());
		result = prime * result + ((timeStopAfterReal == null) ? 0 : timeStopAfterReal.hashCode());
		result = prime * result + ((timeStopAfterTarget == null) ? 0 : timeStopAfterTarget.hashCode());
		result = prime * result + ((timeStopFromReal == null) ? 0 : timeStopFromReal.hashCode());
		result = prime * result + ((timeStopFromTarget == null) ? 0 : timeStopFromTarget.hashCode());
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
		Ride other = (Ride) obj;
		if (breakpointIdAfter == null) {
			if (other.breakpointIdAfter != null)
				return false;
		} else if (!breakpointIdAfter.equals(other.breakpointIdAfter))
			return false;
		if (breakpointIdFrom == null) {
			if (other.breakpointIdFrom != null)
				return false;
		} else if (!breakpointIdFrom.equals(other.breakpointIdFrom))
			return false;
		if (courseNumber == null) {
			if (other.courseNumber != null)
				return false;
		} else if (!courseNumber.equals(other.courseNumber))
			return false;
		if (dtStopAfter == null) {
			if (other.dtStopAfter != null)
				return false;
		} else if (!dtStopAfter.equals(other.dtStopAfter))
			return false;
		if (dtStopFrom == null) {
			if (other.dtStopFrom != null)
				return false;
		} else if (!dtStopFrom.equals(other.dtStopFrom))
			return false;
		if (operationDate == null) {
			if (other.operationDate != null)
				return false;
		} else if (!operationDate.equals(other.operationDate))
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
		if (stopCodeAfter == null) {
			if (other.stopCodeAfter != null)
				return false;
		} else if (!stopCodeAfter.equals(other.stopCodeAfter))
			return false;
		if (stopCodeFrom == null) {
			if (other.stopCodeFrom != null)
				return false;
		} else if (!stopCodeFrom.equals(other.stopCodeFrom))
			return false;
		if (stopIdAfter == null) {
			if (other.stopIdAfter != null)
				return false;
		} else if (!stopIdAfter.equals(other.stopIdAfter))
			return false;
		if (stopIdFrom == null) {
			if (other.stopIdFrom != null)
				return false;
		} else if (!stopIdFrom.equals(other.stopIdFrom))
			return false;
		if (timeStopAfterReal == null) {
			if (other.timeStopAfterReal != null)
				return false;
		} else if (!timeStopAfterReal.equals(other.timeStopAfterReal))
			return false;
		if (timeStopAfterTarget == null) {
			if (other.timeStopAfterTarget != null)
				return false;
		} else if (!timeStopAfterTarget.equals(other.timeStopAfterTarget))
			return false;
		if (timeStopFromReal == null) {
			if (other.timeStopFromReal != null)
				return false;
		} else if (!timeStopFromReal.equals(other.timeStopFromReal))
			return false;
		if (timeStopFromTarget == null) {
			if (other.timeStopFromTarget != null)
				return false;
		} else if (!timeStopFromTarget.equals(other.timeStopFromTarget))
			return false;
		if (vehicleNumber == null) {
			if (other.vehicleNumber != null)
				return false;
		} else if (!vehicleNumber.equals(other.vehicleNumber))
			return false;
		return true;
	}
}
