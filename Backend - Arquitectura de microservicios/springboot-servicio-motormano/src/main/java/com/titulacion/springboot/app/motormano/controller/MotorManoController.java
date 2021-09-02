package com.titulacion.springboot.app.motormano.controller;

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
import com.titulacion.springboot.app.motormano.models.entity.MotorMano;
import com.titulacion.springboot.app.motormano.models.service.IMotorManoService;

@CrossOrigin
@RefreshScope
@RestController
public class MotorManoController {

	@Autowired
	@Qualifier("serviceRestTemplate")
	private IMotorManoService motorManoService;
	
	
	@GetMapping("/lista_local")
	public List<MotorMano> detalle() {
		return motorManoService.findAll();
	}
	
	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@PostMapping("/enviar")
	@ResponseStatus(HttpStatus.OK)
	public MotorMano guardar(@RequestBody MotorMano motorMano) {
		MotorMano respuesta = enviar(motorMano);
		motorManoService.save(motorMano);
		return respuesta;
	}
	
	@PostMapping
	public MotorMano enviar(@RequestBody MotorMano motorMano) {
		MotorMano respuesta = motorManoService.enviar(motorMano);
		return respuesta;
	}
	
	public MotorMano metodoAlternativo(@RequestBody MotorMano motorMano) {
		MotorMano motorManoError = new MotorMano();
		motorManoError.setId(motorMano.getId());
		motorManoError.setFecha(motorMano.getFecha());
		motorManoError.setMovimiento("Error: El motor de la mano no pudo moverse.");
		motorManoService.save(motorManoError);
		return motorManoError;
	}
	
}
