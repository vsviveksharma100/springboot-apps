package com.rest.client.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.rest.client.core.JsonRestClientService;
import com.rest.client.model.CreateUserRequest;
import com.rest.client.model.CreateUserResponse;
import com.rest.client.model.UpdateUserRequest;
import com.rest.client.model.UpdateUserResponse;

@Service
@ConfigurationProperties(prefix = "application.json.user")
public class UserRestApiClient extends JsonRestClientService {

	private String endpointUrl;

	private static final String CREATE_USER = "/api/users";
	private static final String UPDATE_USER = "/api/users/2";

	public CreateUserResponse createUser(CreateUserRequest user) {
		HttpHeaders headers = new HttpHeaders();

		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		return invokePostRequest(getEndpointUrl() + CREATE_USER, user, headers, CreateUserResponse.class);
	}

	public UpdateUserResponse updateUser(UpdateUserRequest user) {
		HttpHeaders headers = new HttpHeaders();

		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		return invokePutRequest(getEndpointUrl() + UPDATE_USER, user, headers, UpdateUserResponse.class);
	}

	public String getEndpointUrl() {
		return endpointUrl;
	}

	public void setEndpointUrl(String endpointUrl) {
		this.endpointUrl = endpointUrl;
	}

}
