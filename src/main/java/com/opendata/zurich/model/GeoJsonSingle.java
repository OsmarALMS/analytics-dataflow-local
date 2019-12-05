package com.opendata.zurich.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class GeoJsonSingle {

	private String type = "Feature";
	private Geometry geometry;
	private Properties properties = new Properties();
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(String type, List<BigDecimal> coordinates) {
		this.geometry = new Geometry(type, coordinates);
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((geometry == null) ? 0 : geometry.hashCode());
		result = prime * result + ((properties == null) ? 0 : properties.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		GeoJsonSingle other = (GeoJsonSingle) obj;
		if (geometry == null) {
			if (other.geometry != null)
				return false;
		} else if (!geometry.equals(other.geometry))
			return false;
		if (properties == null) {
			if (other.properties != null)
				return false;
		} else if (!properties.equals(other.properties))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public class Geometry implements Serializable{
		private static final long serialVersionUID = 1L;
		
		public Geometry(String type, List<BigDecimal> coordinates) {
			super();
			this.type = type;
			this.coordinates = coordinates;
		}

		private String type;
		private List<BigDecimal> coordinates;
		
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public List<BigDecimal> getCoordinates() {
			return coordinates;
		}
		public void setCoordinates(List<BigDecimal> coordinates) {
			this.coordinates = coordinates;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((coordinates == null) ? 0 : coordinates.hashCode());
			result = prime * result + ((type == null) ? 0 : type.hashCode());
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
			Geometry other = (Geometry) obj;
			if (coordinates == null) {
				if (other.coordinates != null)
					return false;
			} else if (!coordinates.equals(other.coordinates))
				return false;
			if (type == null) {
				if (other.type != null)
					return false;
			} else if (!type.equals(other.type))
				return false;
			return true;
		}
	}
	
	public class Properties implements Serializable{
		private static final long serialVersionUID = 1L;

		private String name = "GeoJson";
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
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
			Properties other = (Properties) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}
	}
	
}
