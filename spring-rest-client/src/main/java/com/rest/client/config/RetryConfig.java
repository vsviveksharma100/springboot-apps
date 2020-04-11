package com.rest.client.config;

public class RetryConfig {

	public RetryConfig() {
		super();
	}

	private boolean enabled = false;
	private int interval = 3;
	private int count = 3;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
