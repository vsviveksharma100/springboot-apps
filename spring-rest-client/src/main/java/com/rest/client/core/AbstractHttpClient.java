package com.rest.client.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.rest.client.config.DataBinding;
import com.rest.client.config.HttpConfig;

public abstract class AbstractHttpClient {

	protected static final Logger logger = LoggerFactory.getLogger(AbstractHttpClient.class);

	private RestTemplate restTemplate;
	private HttpConfig config;
	private DataBinding dataBinding = DataBinding.JSON;

	public AbstractHttpClient() {

	}

	protected void onServiceInit() {
		logger.info("AbstractHttpClient >>>>> ServiceInit");
		this.restTemplate = new RestTemplate();

		// TODO once the service generated, instantiate Retry on enabled flag
	}

	public HttpConfig getConfig() {
		return config;
	}

	public void setConfig(HttpConfig config) {
		this.config = config;
	}

	protected <V> V makeGetRequest(String url, HttpHeaders headers, Class<V> responseClass) {
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<V> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseClass);
		return responseEntity.getBody();
	}

	protected DataBinding getDataBinding() {
		return dataBinding;
	}

}
