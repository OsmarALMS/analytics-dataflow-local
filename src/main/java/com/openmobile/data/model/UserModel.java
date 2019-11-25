package com.openmobile.data.model;

public class UserModel {

	public UserModel(String id, String name, String birthday, String address, String country, String countrycode) {
		this.id = id;
		this.name = name;
		this.birthday = birthday;
		this.address = address;
		this.country = country;
		this.countrycode = countrycode;
	}
	
	private String id;
	private String name;
	private String birthday;
	private String address;
	private String country;
	private String countrycode;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCountrycode() {
		return countrycode;
	}
	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
}
