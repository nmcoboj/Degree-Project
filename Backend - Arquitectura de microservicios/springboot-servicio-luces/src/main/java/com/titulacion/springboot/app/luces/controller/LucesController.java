package com.titulacion.springboot.app.luces.controller;

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
import com.titulacion.springboot.app.luces.models.entity.Luces;
import com.titulacion.springboot.app.luces.models.service.ILucesService;

@CrossOrigin
@RefreshScope
@RestController
public class LucesController {

	@Autowired
	@Qualifier("serviceRestTemplate")
	private ILucesService lucesService;
	
	
	@GetMapping("/lista_local")
	public List<Luces> detalle() {
		return lucesService.findAll();
	}
	
	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@PostMapping("/enviar")
	@ResponseStatus(HttpStatus.OK)
	public Luces guardar(@RequestBody Luces luces) {
		Luces respuesta = enviar(luces);
		lucesService.save(luces);
		return respuesta;
	}
	
	@PostMapping
	public Luces enviar(@RequestBody Luces luces) {
		Luces respuesta = lucesService.enviar(luces);
		return respuesta;
	}
	
	public Luces metodoAlternativo(@RequestBody Luces luces) {
		Luces lucesError = new Luces();
		lucesError.setId(luces.getId());
		lucesError.setFecha(luces.getFecha());
		lucesError.setAccion("Error: No se pudo acceder a las luces.");
		lucesService.save(lucesError);
		return lucesError;
	}
	
}
