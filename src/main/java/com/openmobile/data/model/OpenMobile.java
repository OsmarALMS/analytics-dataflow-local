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
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((start_time == null) ? 0 : start_time.hashCode());
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
			Parameters other = (Parameters) obj;
			if (start_time == null) {
				if (other.start_time != null)
					return false;
			} else if (!start_time.equals(other.start_time))
				return false;
			return true;
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
			
			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result + ((manufacturer == null) ? 0 : manufacturer.hashCode());
				result = prime * result + ((model == null) ? 0 : model.hashCode());
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
				DeviceInfo other = (DeviceInfo) obj;
				if (manufacturer == null) {
					if (other.manufacturer != null)
						return false;
				} else if (!manufacturer.equals(other.manufacturer))
					return false;
				if (model == null) {
					if (other.model != null)
						return false;
				} else if (!model.equals(other.model))
					return false;
				return true;
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
			
			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
				result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
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
				Location other = (Location) obj;
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
				return true;
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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((country_code == null) ? 0 : country_code.hashCode());
			result = prime * result + ((device_info == null) ? 0 : device_info.hashCode());
			result = prime * result + ((location == null) ? 0 : location.hashCode());
			result = prime * result + ((os_version == null) ? 0 : os_version.hashCode());
			result = prime * result + ((registration_id == null) ? 0 : registration_id.hashCode());
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
			DeviceProperties other = (DeviceProperties) obj;
			if (country_code == null) {
				if (other.country_code != null)
					return false;
			} else if (!country_code.equals(other.country_code))
				return false;
			if (device_info == null) {
				if (other.device_info != null)
					return false;
			} else if (!device_info.equals(other.device_info))
				return false;
			if (location == null) {
				if (other.location != null)
					return false;
			} else if (!location.equals(other.location))
				return false;
			if (os_version == null) {
				if (other.os_version != null)
					return false;
			} else if (!os_version.equals(other.os_version))
				return false;
			if (registration_id == null) {
				if (other.registration_id != null)
					return false;
			} else if (!registration_id.equals(other.registration_id))
				return false;
			return true;
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
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((context_results == null) ? 0 : context_results.hashCode());
			result = prime * result + ((error == null) ? 0 : error.hashCode());
			result = prime * result + ((packet_loss == null) ? 0 : packet_loss.hashCode());
			result = prime * result + ((packets_sent == null) ? 0 : packets_sent.hashCode());
			result = prime * result + ((results == null) ? 0 : results.hashCode());
			result = prime * result + ((target == null) ? 0 : target.hashCode());
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
			Values other = (Values) obj;
			if (context_results == null) {
				if (other.context_results != null)
					return false;
			} else if (!context_results.equals(other.context_results))
				return false;
			if (error == null) {
				if (other.error != null)
					return false;
			} else if (!error.equals(other.error))
				return false;
			if (packet_loss == null) {
				if (other.packet_loss != null)
					return false;
			} else if (!packet_loss.equals(other.packet_loss))
				return false;
			if (packets_sent == null) {
				if (other.packets_sent != null)
					return false;
			} else if (!packets_sent.equals(other.packets_sent))
				return false;
			if (results == null) {
				if (other.results != null)
					return false;
			} else if (!results.equals(other.results))
				return false;
			if (target == null) {
				if (other.target != null)
					return false;
			} else if (!target.equals(other.target))
				return false;
			return true;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((device_properties == null) ? 0 : device_properties.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((values == null) ? 0 : values.hashCode());
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
		OpenMobile other = (OpenMobile) obj;
		if (device_properties == null) {
			if (other.device_properties != null)
				return false;
		} else if (!device_properties.equals(other.device_properties))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		return true;
	}
}
