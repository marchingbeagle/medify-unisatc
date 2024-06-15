package com.satc.medify;

import com.satc.medify.service.DotenvLoadService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MedifyApplication {

	public static void main(String[] args) {
		//DotenvLoadService.load("./src/main/resources/.env");
		SpringApplication.run(MedifyApplication.class, args);
	}

}
