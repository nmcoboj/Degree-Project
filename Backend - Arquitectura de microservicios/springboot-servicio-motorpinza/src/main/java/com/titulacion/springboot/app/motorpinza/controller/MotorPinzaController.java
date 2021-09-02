package com.titulacion.springboot.app.motorpinza.controller;

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
import com.titulacion.springboot.app.motorpinza.models.entity.MotorPinza;
import com.titulacion.springboot.app.motorpinza.models.service.IMotorPinzaService;

@CrossOrigin
@RefreshScope
@RestController
public class MotorPinzaController {

	@Autowired
	@Qualifier("serviceRestTemplate")
	private IMotorPinzaService motorPinzaService;
	
	
	@GetMapping("/lista_local")
	public List<MotorPinza> detalle() {
		return motorPinzaService.findAll();
	}
	
	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@PostMapping("/enviar")
	@ResponseStatus(HttpStatus.OK)
	public MotorPinza guardar(@RequestBody MotorPinza motorPinza) {
		MotorPinza respuesta = enviar(motorPinza);
		motorPinzaService.save(motorPinza);
		return respuesta;
	}
	
	@PostMapping
	public MotorPinza enviar(@RequestBody MotorPinza motorPinza) {
		MotorPinza respuesta = motorPinzaService.enviar(motorPinza);
		return respuesta;
	}
	
	public MotorPinza metodoAlternativo(@RequestBody MotorPinza motorPinza) {
		MotorPinza motorPinzaError = new MotorPinza();
		motorPinzaError.setId(motorPinza.getId());
		motorPinzaError.setFecha(motorPinza.getFecha());
		motorPinzaError.setMovimiento("Error: El motor de la pinza no pudo moverse.");
		motorPinzaService.save(motorPinzaError);
		return motorPinzaError;
	}
	
}
