package com.titulacion.springboot.app.apagarluces.controllers;

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
import com.titulacion.springboot.app.apagarluces.models.entity.ApagarLuces;
import com.titulacion.springboot.app.apagarluces.models.entity.Accion;
import com.titulacion.springboot.app.apagarluces.models.service.IApagarLucesService;

@CrossOrigin
@RefreshScope
@RestController
public class ApagarLucesController {

	Accion accion = new Accion();
	@Autowired
	@Qualifier("serviceRestTemplate")
	private IApagarLucesService apagarLucesService;

	@GetMapping("/lista_local")
	public List<ApagarLuces> detalleLocal() {
		return apagarLucesService.findAll();
	}

	@GetMapping("/listar_acciones")
	public List<Accion> detalle() {
		return apagarLucesService.listarAcciones();

	}

	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@PostMapping("/enviar")
	@ResponseStatus(HttpStatus.OK)
	public Accion guardar(@RequestBody ApagarLuces apagarLuces) {
		Accion accion = new Accion(apagarLuces);
		Accion respuesta = enviar(accion);
		if ("true".equals(respuesta.getAccion())) {
			apagarLucesService.save(apagarLuces);
		}
		return respuesta;
	}

	@PostMapping
	public Accion enviar(@RequestBody Accion accion) {
		Accion respuesta = apagarLucesService.enviar(accion);
		return respuesta;
	}

	public Accion metodoAlternativo(@RequestBody ApagarLuces apagarLuces) {
		Accion accionError = new Accion();
		accionError.setId(apagarLuces.getId());
		accionError.setFecha(apagarLuces.getFecha());
		accionError.setAccion("Error: La luces no pudieron apagarse.");
		ApagarLuces apagarLucesError = new ApagarLuces(accionError);
		apagarLucesService.save(apagarLucesError);
		return accionError;
	}

}
