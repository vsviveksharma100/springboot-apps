package com.rest.client.model;

import java.util.List;

public class CountriesResponse {
	private Metadata meta;
	private List<Country> results;

	public CountriesResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Metadata getMeta() {
		return meta;
	}

	public void setMeta(Metadata meta) {
		this.meta = meta;
	}

	public List<Country> getResults() {
		return results;
	}

	public void setResults(List<Country> results) {
		this.results = results;
	}
}
