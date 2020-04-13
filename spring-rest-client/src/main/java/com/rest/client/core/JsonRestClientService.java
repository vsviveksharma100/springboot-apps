package com.rest.client.core;

import javax.annotation.PostConstruct;

public abstract class JsonRestClientService extends AbstractHttpClient {

	public JsonRestClientService() {
	}

	@PostConstruct
	protected void onServiceInit() {
		super.onServiceInit();
		logger.info("JsonRestClientService >>>>> ServiceInit");
	}
}
