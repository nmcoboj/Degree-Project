package com.titulacion.springboot.app.bajarantebrazo.controllers;


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
import com.titulacion.springboot.app.bajarantebrazo.models.entity.Accion;
import com.titulacion.springboot.app.bajarantebrazo.models.entity.BajarAntebrazo;
import com.titulacion.springboot.app.bajarantebrazo.models.service.IBajarAntebrazoService;

@CrossOrigin
@RefreshScope
@RestController
public class BajarAntebrazoController {
	
	Accion accion = new Accion();
	@Autowired
	@Qualifier("serviceRestTemplate")
	private IBajarAntebrazoService bajarAntebrazoService;
	

	@GetMapping("/lista_local")
	public List<BajarAntebrazo> detalleLocal(){
		return bajarAntebrazoService.findAll();
	}
	
	@GetMapping("/listar_acciones")
	public List<Accion> detalle() {
		return bajarAntebrazoService.listarAcciones();
		
	}
	
	
	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@PostMapping("/enviar")
	@ResponseStatus(HttpStatus.OK)
	public Accion guardar(@RequestBody BajarAntebrazo bajarAntebrazo) {
		Accion accion = new Accion(bajarAntebrazo);
		Accion respuesta = enviar(accion);
		if ("true".equals(respuesta.getMovimiento()) || "full".equals(respuesta.getMovimiento())) {
			bajarAntebrazoService.save(bajarAntebrazo);
		}
		return respuesta;
	}
	
	@PostMapping
	public Accion enviar(@RequestBody Accion accion) {
		Accion respuesta = bajarAntebrazoService.enviar(accion);
		return respuesta;
	}

	public Accion metodoAlternativo(@RequestBody BajarAntebrazo bajarAntebrazo) {
		Accion accionError = new Accion();
		accionError.setId(bajarAntebrazo.getId());
		accionError.setFecha(bajarAntebrazo.getFecha());
		accionError.setMovimiento("Error: El antebrazo no pudo bajar.");
		BajarAntebrazo bajarAntebrazoError = new BajarAntebrazo(accionError);
		bajarAntebrazoService.save(bajarAntebrazoError);
		return accionError;
	}
	
}
