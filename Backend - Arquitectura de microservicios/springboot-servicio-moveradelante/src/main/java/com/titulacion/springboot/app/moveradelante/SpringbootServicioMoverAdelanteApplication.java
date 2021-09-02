package com.titulacion.springboot.app.moveradelante;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableCircuitBreaker 
@EnableEurekaClient
@SpringBootApplication
public class SpringbootServicioMoverAdelanteApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioMoverAdelanteApplication.class, args);
	}

}
