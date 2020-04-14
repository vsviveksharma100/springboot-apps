package com.rest.client.model;

public class CreateUserRequest extends UserRequest {

	public CreateUserRequest() {
		super();
	}

	public CreateUserRequest(String name, String job) {
		super(name, job);
	}

}
