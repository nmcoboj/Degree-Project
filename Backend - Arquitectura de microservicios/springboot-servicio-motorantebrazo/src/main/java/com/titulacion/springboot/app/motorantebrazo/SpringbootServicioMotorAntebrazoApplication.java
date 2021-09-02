package com.titulacion.springboot.app.motorantebrazo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableCircuitBreaker 
@EnableEurekaClient
@SpringBootApplication
public class SpringbootServicioMotorAntebrazoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioMotorAntebrazoApplication.class, args);
	}

}