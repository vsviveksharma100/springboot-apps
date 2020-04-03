package com.example.retry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.retry.dataaccess.DataService;

@Service
public class BusinessService {

	@Autowired
	private DataService service;

	public int getCountryCount() {
		return service.getCountryCount();
	}

}
