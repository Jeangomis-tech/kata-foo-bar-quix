package com.jcgomis.kata_foo_bar_quix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class KataFooBarQuixApplication {

	public static void main(String[] args) {
		SpringApplication.run(KataFooBarQuixApplication.class, args);



	}

}
