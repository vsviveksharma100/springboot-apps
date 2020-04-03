package com.example.retry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.retry.service.BusinessService;

@RestController
@RequestMapping(value = "/air-quality")
public class APIController {

	@Autowired
	private BusinessService businessService;

	@GetMapping(value = "/country/count")
	public ResponseEntity<Integer> getCountriesCount() {
		return new ResponseEntity<>(businessService.getCountryCount(), HttpStatus.OK);
	}

}
