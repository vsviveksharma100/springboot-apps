package com.rest.client.config;

public class HttpConfig {

	private RetryConfig retry;

	public HttpConfig() {
		super();
	}

	public RetryConfig getRetry() {
		return retry;
	}

	public void setRetry(RetryConfig retry) {
		this.retry = retry;
	}

}
