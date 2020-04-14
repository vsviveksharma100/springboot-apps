package com.rest.client.core;

import com.rest.client.config.DataBinding;

public abstract class XmlRestClientService extends AbstractHttpClient {

	public XmlRestClientService() {
	}

	@Override
	protected void onServiceInit() {

	}

	@Override
	protected DataBinding getDataBinding() {
		return DataBinding.XML;
	}

}
