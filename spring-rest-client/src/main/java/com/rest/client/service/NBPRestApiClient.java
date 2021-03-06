package com.rest.client.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.rest.client.core.XmlRestClientService;

import generated.ArrayOfExchangeRatesTable;

@Service
@ConfigurationProperties(prefix = "application.xml.nbp")
public class NBPRestApiClient extends XmlRestClientService {

	private String endpointUrl;

	public ArrayOfExchangeRatesTable getNbpRates() {

		String url = getEndpointUrl();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_XML_VALUE);

		return invokeGetRequest(url, headers, ArrayOfExchangeRatesTable.class);
	}

	public String getEndpointUrl() {
		return endpointUrl;
	}

	public void setEndpointUrl(String endpointUrl) {
		this.endpointUrl = endpointUrl;
	}

}
