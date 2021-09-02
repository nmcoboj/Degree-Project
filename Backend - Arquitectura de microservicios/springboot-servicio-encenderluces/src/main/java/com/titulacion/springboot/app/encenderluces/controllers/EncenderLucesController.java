package com.titulacion.springboot.app.encenderluces.controllers;


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
import com.titulacion.springboot.app.encenderluces.models.entity.Accion;
import com.titulacion.springboot.app.encenderluces.models.entity.EncenderLuces;
import com.titulacion.springboot.app.encenderluces.models.service.IEncenderLucesService;

@CrossOrigin
@RefreshScope
@RestController
public class EncenderLucesController {
	
	Accion accion = new Accion();
	@Autowired
	@Qualifier("serviceRestTemplate")
	private IEncenderLucesService encenderLucesService;
	

	@GetMapping("/lista_local")
	public List<EncenderLuces> detalleLocal(){
		return encenderLucesService.findAll();
	}
	
	@GetMapping("/listar_acciones")
	public List<Accion> detalle() {
		return encenderLucesService.listarAcciones();
		
	}
	
	
	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@PostMapping("/enviar")
	@ResponseStatus(HttpStatus.OK)
	public Accion guardar(@RequestBody EncenderLuces encenderLuces) {
		Accion accion = new Accion(encenderLuces);
		Accion respuesta = enviar(accion);
		if ("true".equals(respuesta.getAccion())) {
			encenderLucesService.save(encenderLuces);
		}
		encenderLucesService.save(encenderLuces);
		return respuesta;
	}
	
	@PostMapping
	public Accion enviar(@RequestBody Accion accion) {
		Accion respuesta = encenderLucesService.enviar(accion);
		return respuesta;
	}

	public Accion metodoAlternativo(@RequestBody EncenderLuces encenderLuces) {
		Accion accionError = new Accion();
		accionError.setId(encenderLuces.getId());
		accionError.setFecha(encenderLuces.getFecha());
		accionError.setAccion("Error: Las luces no pudieron encenderse.");
		EncenderLuces encenderLucesError = new EncenderLuces(accionError);
		encenderLucesService.save(encenderLucesError);
		return accionError;
	}
	
}
