package com.example.OcrService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class FileProperties {
	
	@Autowired
    private Environment environment;
	
	String docType;
	private final String id;
	private final String type;
        
	public FileProperties(String docType, String id, String type) {
		this.docType = docType;
		this.id = id;
		this.type = type;
		// TODO Auto-generated constructor stub
	}
	
	public String getId() {
		return this.id;
	}

	public String getType() {
		 return this.type;
	}
	
	

}
