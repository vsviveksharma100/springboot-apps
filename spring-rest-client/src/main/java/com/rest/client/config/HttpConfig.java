package com.rest.client.config;

public class HttpConfig {

	private RetryConfig retry;

	private int readTimeout = 50000; // 50 secs default
	private int connectionTimeout = 50000; // 50 secs default
	private int connectTimeout = 50000; // 50 secs default

	public HttpConfig() {
		super();
	}

	public RetryConfig getRetry() {
		return retry;
	}

	public void setRetry(RetryConfig retry) {
		this.retry = retry;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

}
