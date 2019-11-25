package com.openmobile.data.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenMobile implements Serializable{
	private static final long serialVersionUID = 1L;

	private Parameters parameters;
	private String timestamp;
	private DeviceProperties device_properties;
	private Values values;
	private String type;
	private String id;
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public class Parameters implements Serializable{
		private static final long serialVersionUID = 1L;
		
		private String start_time;

		public String getStart_time() {
			return start_time;
		}
		public void setStart_time(String start_time) {
			this.start_time = start_time;
		}
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public class DeviceProperties implements Serializable{
		private static final long serialVersionUID = 1L;

		private String registration_id;
		private String os_version;
		private DeviceInfo device_info;
		private Location location;
		private String country_code;
		
		@JsonIgnoreProperties(ignoreUnknown = true)
		public class DeviceInfo implements Serializable{
			private static final long serialVersionUID = 1L;
			
			private String model;
			private String manufacturer;
			public String getModel() {
				return model;
			}
			public void setModel(String model) {
				this.model = model;
			}
			public String getManufacturer() {
				return manufacturer;
			}
			public void setManufacturer(String manufacturer) {
				this.manufacturer = manufacturer;
			}
		}
		
		@JsonIgnoreProperties(ignoreUnknown = true)
		public class Location implements Serializable{
			private static final long serialVersionUID = 1L;

			private String latitude;
			private String longitude;
			
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
		}

		public String getRegistration_id() {
			return registration_id;
		}

		public void setRegistration_id(String registration_id) {
			this.registration_id = registration_id;
		}

		public String getOs_version() {
			return os_version;
		}

		public void setOs_version(String os_version) {
			this.os_version = os_version;
		}

		public DeviceInfo getDevice_info() {
			return device_info;
		}

		public void setDevice_info(DeviceInfo device_info) {
			this.device_info = device_info;
		}

		public Location getLocation() {
			return location;
		}

		public void setLocation(Location location) {
			this.location = location;
		}

		public String getCountry_code() {
			return country_code;
		}

		public void setCountry_code(String country_code) {
			this.country_code = country_code;
		}
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public class Values implements Serializable{
		private static final long serialVersionUID = 1L;

		private String context_results;
		private String packets_sent;
		private String packet_loss;
		private String results;
		private String target;
		private String error;
		
		public String getContext_results() {
			return context_results;
		}
		public void setContext_results(String context_results) {
			this.context_results = context_results;
		}
		public String getPackets_sent() {
			return packets_sent;
		}
		public void setPackets_sent(String packets_sent) {
			this.packets_sent = packets_sent;
		}
		public String getPacket_loss() {
			return packet_loss;
		}
		public void setPacket_loss(String packet_loss) {
			this.packet_loss = packet_loss;
		}
		public String getResults() {
			return results;
		}
		public void setResults(String results) {
			this.results = results;
		}
		public String getTarget() {
			return target;
		}
		public void setTarget(String target) {
			this.target = target;
		}
		public String getError() {
			return error;
		}
		public void setError(String error) {
			this.error = error;
		}
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public DeviceProperties getDevice_properties() {
		return device_properties;
	}
	public void setDevice_properties(DeviceProperties device_properties) {
		this.device_properties = device_properties;
	}
	public Values getValues() {
		return values;
	}
	public void setValues(Values values) {
		this.values = values;
	}
	public Parameters getParameters() {
		return parameters;
	}
	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}
	
}
