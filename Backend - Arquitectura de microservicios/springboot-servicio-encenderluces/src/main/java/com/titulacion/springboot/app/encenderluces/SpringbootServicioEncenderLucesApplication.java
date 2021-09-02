package com.titulacion.springboot.app.encenderluces;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableCircuitBreaker 
@EnableEurekaClient
@SpringBootApplication
public class SpringbootServicioEncenderLucesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioEncenderLucesApplication.class, args);
	}

}
