package com.titulacion.springboot.app.subirbrazo.controllers;


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
import com.titulacion.springboot.app.subirbrazo.models.entity.Accion;
import com.titulacion.springboot.app.subirbrazo.models.entity.SubirBrazo;
import com.titulacion.springboot.app.subirbrazo.models.service.ISubirBrazoService;

@CrossOrigin
@RefreshScope
@RestController
public class SubirBrazoController {
	
	Accion accion = new Accion();
	@Autowired
	@Qualifier("serviceRestTemplate")
	private ISubirBrazoService subirBrazoService;
	

	@GetMapping("/lista_local")
	public List<SubirBrazo> detalleLocal(){
		return subirBrazoService.findAll();
	}
	
	@GetMapping("/listar_acciones")
	public List<Accion> detalle() {
		return subirBrazoService.listarAcciones();
		
	}
	
	
	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@PostMapping("/enviar")
	@ResponseStatus(HttpStatus.OK)
	public Accion guardar(@RequestBody SubirBrazo subirBrazo) {
		Accion accion = new Accion(subirBrazo);
		Accion respuesta = enviar(accion);
		if ("true".equals(respuesta.getMovimiento()) || "full".equals(respuesta.getMovimiento())) {
			subirBrazoService.save(subirBrazo);
		}
		return respuesta;
	}
	
	@PostMapping
	public Accion enviar(@RequestBody Accion accion) {
		Accion respuesta = subirBrazoService.enviar(accion);
		return respuesta;
	}

	public Accion metodoAlternativo(@RequestBody SubirBrazo subirBrazo) {
		Accion accionError = new Accion();
		accionError.setId(subirBrazo.getId());
		accionError.setFecha(subirBrazo.getFecha());
		accionError.setMovimiento("Error: El brazo no pudo subir.");
		SubirBrazo subirBrazoError = new SubirBrazo(accionError);
		subirBrazoService.save(subirBrazoError);
		return accionError;
	}
	
}
