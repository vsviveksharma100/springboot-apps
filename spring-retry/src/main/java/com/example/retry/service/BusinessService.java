package com.example.retry.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.retry.dataaccess.DataService;

@Service
public class BusinessService {

	private Logger logger = LoggerFactory.getLogger(BusinessService.class);

	@Autowired
	private DataService service;

	public int getCountryCount() {
		int countryCount = service.getCountryCount();
		logger.info(" Response received >>>> " + countryCount);
		return countryCount;
	}

}
