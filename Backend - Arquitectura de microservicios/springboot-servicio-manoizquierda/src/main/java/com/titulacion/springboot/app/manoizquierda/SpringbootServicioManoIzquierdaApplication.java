package com.titulacion.springboot.app.manoizquierda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableCircuitBreaker 
@EnableEurekaClient
@SpringBootApplication
public class SpringbootServicioManoIzquierdaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioManoIzquierdaApplication.class, args);
	}

}
