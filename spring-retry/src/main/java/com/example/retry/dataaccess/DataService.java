package com.example.retry.dataaccess;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.retry.datamodel.CountriesResponse;

/**
 * This Implementation is responsible for retrieving Air Quality for Countries
 * over the Air Quality Open API.
 * 
 * @author Vivek Sharma
 *
 */
@Service
public class DataService {

	@Value("${application.air.quality.endpoint-url}")
	private String endpoint;

	@Autowired
	private RetryTemplate retryTemplate;

	@Autowired
	private RestTemplate restTemplate;

	public int getCountryCount() {
		return makeHttpRequest(getEndpoint());
	}

	private int makeHttpRequest(String url) {
		int count = 0;

		try {
			count = retryTemplate.execute(new RetryCallback<Integer, Exception>() {
				@Override
				public Integer doWithRetry(RetryContext context) throws Exception {

					ResponseEntity<CountriesResponse> response = restTemplate.getForEntity(url, CountriesResponse.class,
							new HashMap<>());
					System.out.println(" Received Response >>>>> " + response.getStatusCode());

					// throwing exception to generate retry
					throw new RuntimeException();
				}
			}, new RecoveryCallback<Integer>() {
				@Override
				public Integer recover(RetryContext context) throws Exception {
					System.out.println(" Retry Count >>>>> " + context.getRetryCount());
					return -1;
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return count;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

}
