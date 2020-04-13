package com.rest.client.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

import com.rest.client.config.DataBinding;
import com.rest.client.config.HttpConfig;
import com.rest.client.config.RetryConfig;

public abstract class AbstractHttpClient {

	protected static final Logger logger = LoggerFactory.getLogger(AbstractHttpClient.class);

	private RestTemplate restTemplate;
	private RetryTemplate retryTemplate;
	private HttpConfig config;
	private DataBinding dataBinding = DataBinding.JSON;
	private boolean retryEnabled = false;

	public AbstractHttpClient() {

	}

	protected void onServiceInit() {
		logger.info("AbstractHttpClient >>>>> ServiceInit");

		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(5000);
		factory.setReadTimeout(5000);

		this.restTemplate = new RestTemplate(factory);

		configureRetry();
	}

	public HttpConfig getConfig() {
		return config;
	}

	public void setConfig(HttpConfig config) {
		this.config = config;
	}

	private void configureRetry() {
		if (config == null || config.getRetry() == null || !config.getRetry().isEnabled())
			logger.info("Retry configuration is null. No retry will be available.");
		else {
			this.retryTemplate = getRetryTemplate(config.getRetry());
			retryEnabled = true;
		}
	}

	protected <V> V makeGetRequest(String url, HttpHeaders headers, Class<V> responseClass) {
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		return makeHttpRequest(url, requestEntity, HttpMethod.GET, responseClass);
	}

	private <V> V makeHttpRequest(String url, HttpEntity<?> httpEntity, HttpMethod method, Class<V> responseClass) {
		if (!isRetryEnabled()) {
			ResponseEntity<V> responseEntity = restTemplate.exchange(url, method, httpEntity, responseClass);
			return responseEntity.getBody();
		} else {
			V response = null;
			try {
				response = retryTemplate.execute(new RetryCallback<V, Exception>() {

					@Override
					public V doWithRetry(RetryContext context) throws Exception {
						ResponseEntity<V> responseEntity = restTemplate.exchange(url, method, httpEntity,
								responseClass);
						return responseEntity.getBody();
					}
				}, new RecoveryCallback<V>() {

					@Override
					public V recover(RetryContext context) throws Exception {
						throw new RuntimeException("Retry Failed after " + context.getRetryCount() + " attempts");
					}
				});
			} catch (Exception e) {
				throw new RuntimeException("Failed to execute retry", e);
			}
			return response;
		}
	}

	private RetryTemplate getRetryTemplate(RetryConfig config) {
		RetryTemplate template = new RetryTemplate();

		// Set Retry TIme Interval
		FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
		backOffPolicy.setBackOffPeriod(config.getInterval());
		template.setBackOffPolicy(backOffPolicy);

		// Set Max Attempts
		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(config.getAttempts());
		template.setRetryPolicy(retryPolicy);

		return template;
	}

	protected DataBinding getDataBinding() {
		return dataBinding;
	}

	public boolean isRetryEnabled() {
		return retryEnabled;
	}

}
