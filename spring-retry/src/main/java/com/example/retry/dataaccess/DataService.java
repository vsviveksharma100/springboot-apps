package com.example.retry.dataaccess;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.retry.datamodel.CountriesResponse;

/**
 * This Implementation is responsible for retrieving Air Quality for Countries
 * over the Air Quality Open API.
 * 
 * @author Vivek Sharma
 *
 */
@Service
public class DataService {

	@Value("${application.air.quality.endpoint-url}")
	private String endpoint;

	@Autowired
	private RestTemplate restTemplate;

	public int getCountryCount() {
		ResponseEntity<CountriesResponse> response = this.restTemplate.getForEntity(endpoint, CountriesResponse.class,
				new HashMap<>());
		return response.getBody().getMeta().getFound();
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

}
