package com.example.AuthServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;


@Service
public class RestClient {

	@Autowired
	private RestTemplate template;

	public void Logger(Log log) {
		// TODO Auto-generated method stub
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Log> logging = new HttpEntity<>(log, headers);
		log.setDate(new Date());
		ResponseEntity<?> response =
				template.postForEntity("http://localhost:8084/logger", logging, null);
		//System.out.println(response.getBody());
	}
	

}
