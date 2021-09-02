package com.titulacion.springboot.app.abrirpinza.controllers;


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
import com.titulacion.springboot.app.abrirpinza.models.entity.Accion;
import com.titulacion.springboot.app.abrirpinza.models.entity.AbrirPinza;
import com.titulacion.springboot.app.abrirpinza.models.service.IAbrirPinzaService;

@CrossOrigin
@RefreshScope
@RestController
public class AbrirPinzaController {
	
	Accion accion = new Accion();
	@Autowired
	@Qualifier("serviceRestTemplate")
	private IAbrirPinzaService abrirPinzaService;
	

	@GetMapping("/lista_local")
	public List<AbrirPinza> detalleLocal(){
		return abrirPinzaService.findAll();
	}
	
	@GetMapping("/listar_acciones")
	public List<Accion> detalle() {
		return abrirPinzaService.listarAcciones();
		
	}
	
	
	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@PostMapping("/enviar")
	@ResponseStatus(HttpStatus.OK)
	public Accion guardar(@RequestBody AbrirPinza abrirPinza) {
		Accion accion = new Accion(abrirPinza);
		Accion respuesta = enviar(accion);
		if ("true".equals(respuesta.getMovimiento()) || "full".equals(respuesta.getMovimiento())) {
			abrirPinzaService.save(abrirPinza);
		}
		return respuesta;
	}
	
	@PostMapping
	public Accion enviar(@RequestBody Accion accion) {
		Accion respuesta = abrirPinzaService.enviar(accion);
		return respuesta;
	}

	public Accion metodoAlternativo(@RequestBody AbrirPinza abrirPinza) {
		Accion accionError = new Accion();
		accionError.setId(abrirPinza.getId());
		accionError.setFecha(abrirPinza.getFecha());
		accionError.setMovimiento("Error: La pinza no pudo abrirse.");
		AbrirPinza abrirPinzaError = new AbrirPinza(accionError);
		abrirPinzaService.save(abrirPinzaError);
		return accionError;
	}
	
}
