package com.titulacion.springboot.app.motorderecho.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.titulacion.springboot.app.motorderecho.models.entity.MotorDerecho;
import com.titulacion.springboot.app.motorderecho.models.service.IMotorDerechoService;

@CrossOrigin
@RefreshScope
@RestController
public class MotorDerechoController {

	@Autowired
	@Qualifier("serviceRestTemplate")
	private IMotorDerechoService motorDerechoService;
	
	
	@GetMapping("/lista_local")
	public List<MotorDerecho> detalle() {
		return motorDerechoService.findAll();
	}
	
	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@PostMapping("/enviar")
	@ResponseStatus(HttpStatus.OK)
	public MotorDerecho guardar(@RequestBody MotorDerecho motorDerecho) {
		MotorDerecho respuesta = enviar(motorDerecho);
		motorDerechoService.save(motorDerecho);
		return respuesta;
	}
	
	public MotorDerecho enviar(@RequestBody MotorDerecho motorDerecho) {
		MotorDerecho respuesta = motorDerechoService.enviar(motorDerecho);
		return respuesta;
	}
	
	public MotorDerecho metodoAlternativo(@RequestBody MotorDerecho motorDerecho) {
		MotorDerecho motorDerechoError = new MotorDerecho();
		motorDerechoError.setId(motorDerecho.getId());
		motorDerechoError.setFecha(motorDerecho.getFecha());
		motorDerechoError.setMovimiento("Error: El motor derecho no pudo moverse.");
		motorDerechoService.save(motorDerechoError);
		return motorDerechoError;
	}
	
}
