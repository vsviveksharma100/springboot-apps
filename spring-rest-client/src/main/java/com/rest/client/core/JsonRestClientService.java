package com.rest.client.core;

import com.rest.client.config.DataBinding;

public abstract class JsonRestClientService extends AbstractHttpClient {

	public JsonRestClientService() {
	}

	protected void onServiceInit() {
	}

	@Override
	protected DataBinding getDataBinding() {
		return DataBinding.JSON;
	}
}
