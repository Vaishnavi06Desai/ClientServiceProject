package com.example.OcrService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@ConfigurationProperties
public class FileAcceptController {
	private final StorageService storageService;


	@Autowired
	public FileAcceptController(StorageService storageService) throws IOException {
		this.storageService = storageService;
	}

	@Autowired
	RestClient client;

	Log log = new Log();

	@PostMapping("/ocrservice/documents/newdoc")
	public ResponseEntity<String> handleFileUpload(@RequestHeader(value = "Authorization") String token, @RequestBody FileDetails fd, RedirectAttributes redirectAttributes) {

		//String username = null;
		JwtResponse jwtToken = new JwtResponse();
		// JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token

		if (token != null && token.startsWith("Bearer ")) {
			jwtToken.setToken(token.substring(7));

			if (jwtToken.getToken() != null){

				log.setLogString("Validating token......");
				log.setSender("OCR Service");
				client.Logger(log);
				//Validate Token
				HttpStatus validate = client.Validate(jwtToken).getStatusCode();
				if(validate == HttpStatus.OK)
				{
					log.setLogString("Token Validated. Processing file....");
					log.setSender("OCR Service");
					client.Logger(log);

					String file = fd.getfile();
					String docType = fd.getdocType();
					String name = fd.getFileName();

					JSONObject json = new JSONObject();
					ClassLoader loader = Thread.currentThread().getContextClassLoader();
					Properties properties = new Properties();
					try (InputStream resourceStream = loader.getResourceAsStream("application.properties")) {
						properties.load(resourceStream);
					} catch (IOException e) {
						return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
					}

					Set states;
					String str;
					states = properties.keySet(); // get set-view of keys
					Iterator itr = states.iterator();

					while (itr.hasNext()) {
						str = (String) itr.next();
						if (str.startsWith(docType))
						{
							json.put(str, properties.getProperty(str));
						}
					}
					storageService.store(file, name);

					log.setLogString("Returning processed data...");
					log.setSender("OCR Service");
					client.Logger(log);
					return ResponseEntity.ok(json.toString());
				}

				else{
					log.setLogString("Token is invalid." + "\n" + "Error: " + validate);
					log.setSender("OCR Service");
					client.Logger(log);
					return new ResponseEntity<String>("", HttpStatus.UNAUTHORIZED);
				}
			}

			else {
				log.setLogString("Error: JWT is null.");
				log.setSender("OCR Service");
				client.Logger(log);
				return new ResponseEntity<String>("", HttpStatus.UNAUTHORIZED);
			}
		}
		else {
			log.setLogString("Error: JWT Token not found. Token does not begin with Bearer String.");
			log.setSender("OCR Service");
			client.Logger(log);
			return new ResponseEntity<String>("", HttpStatus.UNAUTHORIZED);
			//logger.warn("JWT Token does not begin with Bearer String");
		}
	}

}
