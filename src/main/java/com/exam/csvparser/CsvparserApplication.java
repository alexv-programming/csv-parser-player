package com.exam.csvparser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CsvparserApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsvparserApplication.class, args);
	}

}
