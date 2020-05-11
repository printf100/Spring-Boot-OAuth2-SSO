package com.sample.ssoclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class ASystemApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ASystemApplication.class);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ASystemApplication.class);
	}

}
