package com.rest.client.core;

import java.io.StringReader;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.http.HttpHeaders;

import com.rest.client.config.DataBinding;

public class XmlRestClientService extends AbstractHttpClient {

	private Unmarshaller unmarshaller;

	public XmlRestClientService() {
	}

	@PostConstruct
	protected void onServiceInit() {
		super.onServiceInit();
		logger.info("XmlRestClientService >>>>> ServiceInit");

		this.unmarshaller = createUnmarshaller();
	}

	// Override in case DataBinding is XML and JABX conversion is needed
	protected String getJaxbPackeges() {
		return null;
	}

	@Override
	protected <V> V makeGetRequest(String url, HttpHeaders headers, Class<V> responseClass) {
		String strResponse = super.makeGetRequest(url, headers, String.class);
		return convert(strResponse, responseClass);
	}

	@SuppressWarnings("unchecked")
	private <V> V convert(String xmlStr, Class<V> responseClass) {
		if (xmlStr == null || xmlStr.isEmpty())
			throw new RuntimeException("Http response is must not empty. Invalid response.");

		try {
			return (V) unmarshaller.unmarshal(new StringReader(xmlStr));
		} catch (JAXBException e) {
			throw new RuntimeException("Failed to convert Http xml response", e);
		}

	}

	private Unmarshaller createUnmarshaller() {
		String jaxbPackeges = getJaxbPackeges();
		if (jaxbPackeges == null || jaxbPackeges.isEmpty())
			throw new RuntimeException("JAXB Packages are required for conversion. No Jaxb packages found.");

		try {
			JAXBContext context = JAXBContext.newInstance(jaxbPackeges);
			return context.createUnmarshaller();
		} catch (JAXBException e) {
			throw new RuntimeException("Failed to create Unmarshaller", e);
		}
	}

	@Override
	protected DataBinding getDataBinding() {
		return DataBinding.XML;
	}
}
