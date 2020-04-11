package com.rest.client.model;

public class Country {

	private String code;
	private long count;
	private long locations;
	private long cities;
	private String name;

	public Country() {
	}

	public Country(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public long getLocations() {
		return locations;
	}

	public void setLocations(long locations) {
		this.locations = locations;
	}

	public long getCities() {
		return cities;
	}

	public void setCities(long cities) {
		this.cities = cities;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
