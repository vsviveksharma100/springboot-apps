package com.rest.client.core;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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
import com.rest.client.utils.AppUtils;

public abstract class AbstractHttpClient {

	protected static final Logger logger = LoggerFactory.getLogger(AbstractHttpClient.class);

	private RestTemplate restTemplate;
	private RetryTemplate retryTemplate;
	private HttpConfig config;
	private boolean retryEnabled = false;

	public AbstractHttpClient() {

	}

	@PostConstruct
	protected void onInit() {
		configure();

		onServiceInit();
	}

	private void configure() {
		configureRestTemplate();
		configureRetry();
	}

	private void configureRestTemplate() {
		if (config == null) {
			logger.info("Http config is null. Rest Template will be configured with defalt values");
			this.restTemplate = getRestTemplate(new HttpConfig());
		} else {
			this.restTemplate = getRestTemplate(config);
		}
	}

	private RestTemplate getRestTemplate(HttpConfig httpConfig) {

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setConnectionRequestTimeout(httpConfig.getConnectionTimeout());
		factory.setConnectTimeout(httpConfig.getConnectTimeout());
		factory.setReadTimeout(httpConfig.getReadTimeout());

		return new RestTemplate(factory);
	}

	private void configureRetry() {
		if (config == null || config.getRetry() == null || !config.getRetry().isEnabled())
			logger.info("Retry configuration is null. No retry will be available.");
		else {
			this.retryTemplate = getRetryTemplate(config.getRetry());
			retryEnabled = true;
		}
	}

	protected <V> V invokeGetRequest(String url, HttpHeaders headers, Class<V> responseClass) {

		HttpEntity<String> requestEntity = getHttpEntity(headers, null);
		return invokeHttpRequest(url, requestEntity, HttpMethod.GET, responseClass);
	}

	protected <V, T> V invokePostRequest(String url, T payload, HttpHeaders headers, Class<V> responseClass) {

		HttpEntity<T> requestEntity = getHttpEntity(headers, payload);
		return invokeHttpRequest(url, requestEntity, HttpMethod.POST, responseClass);
	}

	protected <V, T> V invokePutRequest(String url, T payload, HttpHeaders headers, Class<V> responseClass) {

		HttpEntity<T> requestEntity = getHttpEntity(headers, payload);
		return invokeHttpRequest(url, requestEntity, HttpMethod.PUT, responseClass);
	}

	private <T> HttpEntity<T> getHttpEntity(HttpHeaders headers, T payload) {
		if (payload == null)
			return new HttpEntity<>(headers);

		return new HttpEntity<>(payload, headers);
	}

	private <V> V invokeHttpRequest(String url, HttpEntity<?> httpEntity, HttpMethod method, Class<V> responseClass) {
		validate(url, responseClass);

		if (!retryEnabled) {
			return invoke(url, httpEntity, method, responseClass);
		} else {
			V response = null;
			try {
				response = retryTemplate.execute(new RetryCallback<V, Exception>() {

					@Override
					public V doWithRetry(RetryContext context) throws Exception {
						return invoke(url, httpEntity, method, responseClass);
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

	private <V> V invoke(String url, HttpEntity<?> httpEntity, HttpMethod method, Class<V> responseClass) {
		ResponseEntity<V> responseEntity = restTemplate.exchange(url, method, httpEntity, responseClass);
		return responseEntity.getBody();
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

	protected abstract DataBinding getDataBinding();

	protected abstract void onServiceInit();

	/**
	 * Validate's request url and response class.
	 * 
	 * @param url
	 * @param responseClass
	 * @return
	 */
	private boolean validate(String url, Class<?> responseClass) {
		if (AppUtils.isBlankStr(url))
			throw new RuntimeException("Url must not be empty");

		if (responseClass == null)
			throw new RuntimeException("Response class must not be null");

		return true;
	}

	public HttpConfig getConfig() {
		return config;
	}

	public void setConfig(HttpConfig config) {
		this.config = config;
	}
}
