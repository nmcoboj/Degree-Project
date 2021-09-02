package com.titulacion.springboot.app.cambiarcolorluces.controllers;


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
import com.titulacion.springboot.app.cambiarcolorluces.models.entity.Accion;
import com.titulacion.springboot.app.cambiarcolorluces.models.entity.CambiarColorLuces;
import com.titulacion.springboot.app.cambiarcolorluces.models.service.ICambiarColorLucesService;

@CrossOrigin
@RefreshScope
@RestController
public class CambiarColorLucesController {
	
	Accion accion = new Accion();
	@Autowired
	@Qualifier("serviceRestTemplate")
	private ICambiarColorLucesService cambiarColorLucesService;
	

	@GetMapping("/lista_local")
	public List<CambiarColorLuces> detalleLocal(){
		return cambiarColorLucesService.findAll();
	}
	
	@GetMapping("/listar_acciones")
	public List<Accion> detalle() {
		return cambiarColorLucesService.listarAcciones();
		
	}
	
	
	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@PostMapping("/enviar")
	@ResponseStatus(HttpStatus.OK)
	public Accion guardar(@RequestBody CambiarColorLuces cambiarColorLuces) {
		Accion accion = new Accion(cambiarColorLuces);
		Accion respuesta = enviar(accion);
		if ("true".equals(respuesta.getAccion())) {
			cambiarColorLucesService.save(cambiarColorLuces);
		}
		return respuesta;
	}
	
	@PostMapping
	public Accion enviar(@RequestBody Accion accion) {
		Accion respuesta = cambiarColorLucesService.enviar(accion);
		return respuesta;
	}

	public Accion metodoAlternativo(@RequestBody CambiarColorLuces cambiarColorLuces) {
		Accion accionError = new Accion();
		accionError.setId(cambiarColorLuces.getId());
		accionError.setFecha(cambiarColorLuces.getFecha());
		accionError.setAccion("Error: La luces no pudieron cambiarse de color.");
		CambiarColorLuces encenderLucesError = new CambiarColorLuces(accionError);
		cambiarColorLucesService.save(encenderLucesError);
		return accionError;
	}
	
}
