package com.titulacion.springboot.app.motorantebrazo.controller;

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
import com.titulacion.springboot.app.motorantebrazo.models.entity.MotorAntebrazo;
import com.titulacion.springboot.app.motorantebrazo.models.service.IMotorAntebrazoService;

@CrossOrigin
@RefreshScope
@RestController
public class MotorAntebrazoController {

	@Autowired
	@Qualifier("serviceRestTemplate")
	private IMotorAntebrazoService motorAntebrazoService;
	
	
	@GetMapping("/lista_local")
	public List<MotorAntebrazo> detalle() {
		return motorAntebrazoService.findAll();
	}
	
	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@PostMapping("/enviar")
	@ResponseStatus(HttpStatus.OK)
	public MotorAntebrazo guardar(@RequestBody MotorAntebrazo motorAntebrazo) {
		MotorAntebrazo respuesta = enviar(motorAntebrazo);
		motorAntebrazoService.save(motorAntebrazo);
		return respuesta;
	}
	
	@PostMapping
	public MotorAntebrazo enviar(@RequestBody MotorAntebrazo motorAntebrazo) {
		MotorAntebrazo respuesta = motorAntebrazoService.enviar(motorAntebrazo);
		return respuesta;
	}
	
	public MotorAntebrazo metodoAlternativo(@RequestBody MotorAntebrazo motorAntebrazo) {
		MotorAntebrazo motorAntebrazoError = new MotorAntebrazo();
		motorAntebrazoError.setId(motorAntebrazo.getId());
		motorAntebrazoError.setFecha(motorAntebrazo.getFecha());
		motorAntebrazoError.setMovimiento("Error: El motor del antebrazo no pudo moverse.");
		motorAntebrazoService.save(motorAntebrazoError);
		return motorAntebrazoError;
	}
	
}
