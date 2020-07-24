package com.example.OcrService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Service 
public class StorageService {
	void init() {}

	@Value("${filename}")
	private String filepath;

	@Autowired
	RestClient client;

	Log log = new Log();

	void store(String file, String name) {
		log.setLogString("Decoding file from Base64....");
		log.setSender("OCR Service");
		client.Logger(log);
		Path fileNameAndPath = Paths.get(filepath, name);
		byte [] decode_data = Base64.getDecoder().decode(file);
		try {
			log.setLogString("Storing file to configured path...");
			log.setSender("OCR Service");
			client.Logger(log);
			Files.write(fileNameAndPath, decode_data);
		} catch (IOException e) {
			log.setLogString("Error: Unable to store file." + "\n" + e.getStackTrace());
			log.setSender("OCR Service");
			client.Logger(log);
			e.printStackTrace();
		}
	}

}
