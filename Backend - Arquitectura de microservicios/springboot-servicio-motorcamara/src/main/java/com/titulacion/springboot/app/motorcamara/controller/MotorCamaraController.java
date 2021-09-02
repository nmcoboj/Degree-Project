package com.titulacion.springboot.app.motorcamara.controller;

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
import com.titulacion.springboot.app.motorcamara.models.entity.MotorCamara;
import com.titulacion.springboot.app.motorcamara.models.service.IMotorCamaraService;

@CrossOrigin
@RefreshScope
@RestController
public class MotorCamaraController {

	@Autowired
	@Qualifier("serviceRestTemplate")
	private IMotorCamaraService motorCamaraService;
	
	
	@GetMapping("/lista_local")
	public List<MotorCamara> detalle() {
		return motorCamaraService.findAll();
	}
	
	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@PostMapping("/enviar")
	@ResponseStatus(HttpStatus.OK)
	public MotorCamara guardar(@RequestBody MotorCamara motorCamara) {
		MotorCamara respuesta = enviar(motorCamara);
		motorCamaraService.save(motorCamara);
		return respuesta;
	}
	
	@PostMapping
	public MotorCamara enviar(@RequestBody MotorCamara motorCamara) {
		MotorCamara respuesta = motorCamaraService.enviar(motorCamara);
		return respuesta;
	}
	
	public MotorCamara metodoAlternativo(@RequestBody MotorCamara motorCamara) {
		MotorCamara motorCamaraError = new MotorCamara();
		motorCamaraError.setId(motorCamara.getId());
		motorCamaraError.setFecha(motorCamara.getFecha());
		motorCamaraError.setMovimiento("Error: El motor de la cámara no pudo moverse.");
		motorCamaraService.save(motorCamaraError);
		return motorCamaraError;
	}
	
}
