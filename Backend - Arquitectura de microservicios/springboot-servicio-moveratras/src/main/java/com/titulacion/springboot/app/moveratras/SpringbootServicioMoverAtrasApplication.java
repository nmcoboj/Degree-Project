package com.titulacion.springboot.app.moveratras;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableCircuitBreaker 
@EnableEurekaClient
@SpringBootApplication
public class SpringbootServicioMoverAtrasApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioMoverAtrasApplication.class, args);
	}

}
