package com.github.mereiamangeldin.transactions_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TransactionsMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionsMicroserviceApplication.class, args);
	}

}
