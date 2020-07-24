package com.example.OcrService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;


@Service
public class RestClient {

	@Autowired
	private RestTemplate template;

	public ResponseEntity<String> Validate(JwtResponse token) {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<JwtResponse> validateToken = new HttpEntity<>(token, headers);
		ResponseEntity<String> response;
		try {
			response = template.postForEntity("http://localhost:8082/validate", validateToken, String.class);
			return ResponseEntity.ok(response.getBody());
		}
		catch (HttpStatusCodeException ex)
		{
			return new ResponseEntity<String >("", HttpStatus.UNAUTHORIZED);
		}
		// TODO Auto-generated method stub
		//return response.getBody();
	}

	public void Logger(Log log) {
		// TODO Auto-generated method stub
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Log> logging = new HttpEntity<>(log, headers);
		log.setDate(new Date());
		ResponseEntity<?> response =
				template.postForEntity("http://localhost:8084/logger", logging, null);
	}
	

}
