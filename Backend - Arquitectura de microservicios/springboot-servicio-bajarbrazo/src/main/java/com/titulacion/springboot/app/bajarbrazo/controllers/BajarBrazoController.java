package com.titulacion.springboot.app.bajarbrazo.controllers;


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
import com.titulacion.springboot.app.bajarbrazo.models.entity.Accion;
import com.titulacion.springboot.app.bajarbrazo.models.entity.BajarBrazo;
import com.titulacion.springboot.app.bajarbrazo.models.service.IBajarBrazoService;

@CrossOrigin
@RefreshScope
@RestController
public class BajarBrazoController {
	
	Accion accion = new Accion();
	@Autowired
	@Qualifier("serviceRestTemplate")
	private IBajarBrazoService bajarBrazoService;
	

	@GetMapping("/lista_local")
	public List<BajarBrazo> detalleLocal(){
		return bajarBrazoService.findAll();
	}
	
	@GetMapping("/listar_acciones")
	public List<Accion> detalle() {
		return bajarBrazoService.listarAcciones();
		
	}
	
	
	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@PostMapping("/enviar")
	@ResponseStatus(HttpStatus.OK)
	public Accion guardar(@RequestBody BajarBrazo bajarBrazo) {
		Accion accion = new Accion(bajarBrazo);
		Accion respuesta = enviar(accion);
		if ("true".equals(respuesta.getMovimiento()) || "full".equals(respuesta.getMovimiento())) {
			bajarBrazoService.save(bajarBrazo);
		}
		return respuesta;
	}
	
	@PostMapping
	public Accion enviar(@RequestBody Accion accion) {
		Accion respuesta = bajarBrazoService.enviar(accion);
		return respuesta;
	}

	public Accion metodoAlternativo(@RequestBody BajarBrazo bajarBrazo) {
		Accion accionError = new Accion();
		accionError.setId(bajarBrazo.getId());
		accionError.setFecha(bajarBrazo.getFecha());
		accionError.setMovimiento("Error: El brazo no pudo bajar.");
		BajarBrazo bajarBrazoError = new BajarBrazo(accionError);
		bajarBrazoService.save(bajarBrazoError);
		return accionError;
	}
	
}
