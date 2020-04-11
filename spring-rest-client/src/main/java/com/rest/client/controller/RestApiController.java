package com.rest.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.client.model.CountriesResponse;
import com.rest.client.service.AirQualityRestApiClient;
import com.rest.client.service.NBPRestApiClient;

import generated.ArrayOfExchangeRatesTable;


@RestController
public class RestApiController {

	@Autowired
	private AirQualityRestApiClient airQualityService;

	@Autowired
	private NBPRestApiClient nbpService;

	@GetMapping(value = "/air-quality/countries")
	public ResponseEntity<CountriesResponse> getRegionDetails() {
		return new ResponseEntity<>(airQualityService.getCountries(), HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/nbp/exchange-rates", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<ArrayOfExchangeRatesTable> getExchangeRates() {
		return new ResponseEntity<>(nbpService.getNbpRates(), HttpStatus.ACCEPTED);
	}
}
