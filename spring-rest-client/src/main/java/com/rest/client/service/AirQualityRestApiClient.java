package com.rest.client.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import com.rest.client.core.JsonRestClientService;
import com.rest.client.model.CountriesResponse;

@Service
@ConfigurationProperties(prefix = "application.json.air-quality")
public class AirQualityRestApiClient extends JsonRestClientService {

	private String endpointUrl;

	public CountriesResponse getCountries() {
		logger.info("Request");
		String url = getEndpointUrl();
		return invokeGetRequest(url, null, CountriesResponse.class);
	}

	public void recover(Exception e, String url) {
		logger.error(url, e);
	}

	public String getEndpointUrl() {
		return endpointUrl;
	}

	public void setEndpointUrl(String endpointUrl) {
		this.endpointUrl = endpointUrl;
	}

}
