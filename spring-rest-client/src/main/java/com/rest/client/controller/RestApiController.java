package com.rest.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rest.client.model.CountriesResponse;
import com.rest.client.model.CreateUserRequest;
import com.rest.client.model.CreateUserResponse;
import com.rest.client.model.UpdateUserRequest;
import com.rest.client.model.UpdateUserResponse;
import com.rest.client.service.AirQualityRestApiClient;
import com.rest.client.service.NBPRestApiClient;
import com.rest.client.service.UserRestApiClient;

import generated.ArrayOfExchangeRatesTable;

@RestController
public class RestApiController {

	@Autowired
	private AirQualityRestApiClient airQualityService;

	@Autowired
	private NBPRestApiClient nbpService;

	@Autowired
	private UserRestApiClient userService;

	@GetMapping(value = "/air-quality/countries")
	public ResponseEntity<CountriesResponse> getRegionDetails() {
		return new ResponseEntity<>(airQualityService.getCountries(), HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/nbp/exchange-rates", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<ArrayOfExchangeRatesTable> getExchangeRates() {
		return new ResponseEntity<>(nbpService.getNbpRates(), HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/user/create")
	public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest user) {
		return new ResponseEntity<>(userService.createUser(user), HttpStatus.ACCEPTED);
	}

	@PutMapping(value = "/user/update")
	public ResponseEntity<UpdateUserResponse> updateUser(@RequestBody UpdateUserRequest user) {
		return new ResponseEntity<>(userService.updateUser(user), HttpStatus.ACCEPTED);
	}
}
