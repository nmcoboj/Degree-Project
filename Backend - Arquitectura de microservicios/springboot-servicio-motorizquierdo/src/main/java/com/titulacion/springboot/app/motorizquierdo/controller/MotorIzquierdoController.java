package com.titulacion.springboot.app.motorizquierdo.controller;

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
import com.titulacion.springboot.app.motorizquierdo.models.entity.MotorIzquierdo;
import com.titulacion.springboot.app.motorizquierdo.models.service.IMotorIzquierdoService;

@CrossOrigin
@RefreshScope
@RestController
public class MotorIzquierdoController {

	@Autowired
	@Qualifier("serviceRestTemplate")
	private IMotorIzquierdoService motorIzquierdoService;
	
	
	@GetMapping("/lista_local")
	public List<MotorIzquierdo> detalle() {
		return motorIzquierdoService.findAll();
	}
	
	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@PostMapping("/enviar")
	@ResponseStatus(HttpStatus.OK)
	public MotorIzquierdo guardar(@RequestBody MotorIzquierdo motorIzquierdo) {
		MotorIzquierdo respuesta = enviar(motorIzquierdo);
		motorIzquierdoService.save(motorIzquierdo);
		return respuesta;
	}
	
	public MotorIzquierdo enviar(@RequestBody MotorIzquierdo motorIzquierdo) {
		MotorIzquierdo respuesta = motorIzquierdoService.enviar(motorIzquierdo);
		return respuesta;
	}
	
	public MotorIzquierdo metodoAlternativo(@RequestBody MotorIzquierdo motorIzquierdo) {
		MotorIzquierdo motorIzquierdoError = new MotorIzquierdo();
		motorIzquierdoError.setId(motorIzquierdo.getId());
		motorIzquierdoError.setFecha(motorIzquierdo.getFecha());
		motorIzquierdoError.setMovimiento("Error: El motor izquierdo no pudo moverse.");
		motorIzquierdoService.save(motorIzquierdoError);
		return motorIzquierdoError;
	}
	
}
