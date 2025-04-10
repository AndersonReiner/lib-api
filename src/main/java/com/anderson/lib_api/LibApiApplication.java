package com.anderson.lib_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.anderson.lib_api.configuration.EnvLoader;

@SpringBootApplication
public class LibApiApplication {

	public static void main(String[] args) {
		EnvLoader.load();
		SpringApplication.run(LibApiApplication.class, args);
	}

}
