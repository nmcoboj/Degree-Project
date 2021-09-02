package com.titulacion.springboot.app.motorbrazo.controller;

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
import com.titulacion.springboot.app.motorbrazo.models.entity.MotorBrazo;
import com.titulacion.springboot.app.motorbrazo.models.service.IMotorBrazoService;

@CrossOrigin
@RefreshScope
@RestController
public class MotorBrazoController {

	@Autowired
	@Qualifier("serviceRestTemplate")
	private IMotorBrazoService motorBrazoService;
	
	
	@GetMapping("/lista_local")
	public List<MotorBrazo> detalle() {
		return motorBrazoService.findAll();
	}
	
	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@PostMapping("/enviar")
	@ResponseStatus(HttpStatus.OK)
	public MotorBrazo guardar(@RequestBody MotorBrazo motorBrazo) {
		MotorBrazo respuesta = enviar(motorBrazo);
		motorBrazoService.save(motorBrazo);
		return respuesta;
	}
	
	@PostMapping
	public MotorBrazo enviar(@RequestBody MotorBrazo motorBrazo) {
		MotorBrazo respuesta = motorBrazoService.enviar(motorBrazo);
		return respuesta;
	}
	
	public MotorBrazo metodoAlternativo(@RequestBody MotorBrazo motorBrazo) {
		MotorBrazo motorBrazoError = new MotorBrazo();
		motorBrazoError.setId(motorBrazo.getId());
		motorBrazoError.setFecha(motorBrazo.getFecha());
		motorBrazoError.setMovimiento("Error: El motor del brazo no pudo moverse.");
		motorBrazoService.save(motorBrazoError);
		return motorBrazoError;
	}
	
}
