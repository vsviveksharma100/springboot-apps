package com.rest.client.model;

public class UpdateUserResponse extends UserRequest {

	private String updatedAt;

	public UpdateUserResponse() {

	}

	public UpdateUserResponse(String name, String job) {
		super(name, job);
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
}
