package com.mi.event_api_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EventApiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventApiServiceApplication.class, args);
	}

}
