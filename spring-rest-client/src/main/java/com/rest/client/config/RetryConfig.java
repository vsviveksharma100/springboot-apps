package com.rest.client.config;

public class RetryConfig {

	public RetryConfig() {
		super();
	}

	private boolean enabled = false;
	private int interval = 3000;
	private int attempts = 3;

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

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
}
