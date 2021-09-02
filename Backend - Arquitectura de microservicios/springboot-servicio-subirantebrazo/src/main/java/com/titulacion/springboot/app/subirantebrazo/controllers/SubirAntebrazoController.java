package com.titulacion.springboot.app.subirantebrazo.controllers;


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
import com.titulacion.springboot.app.subirantebrazo.models.entity.Accion;
import com.titulacion.springboot.app.subirantebrazo.models.entity.SubirAntebrazo;
import com.titulacion.springboot.app.subirantebrazo.models.service.ISubirAntebrazoService;

@CrossOrigin
@RefreshScope
@RestController
public class SubirAntebrazoController {
	
	Accion accion = new Accion();
	@Autowired
	@Qualifier("serviceRestTemplate")
	private ISubirAntebrazoService subirAntebrazoService;
	

	@GetMapping("/lista_local")
	public List<SubirAntebrazo> detalleLocal(){
		return subirAntebrazoService.findAll();
	}
	
	@GetMapping("/listar_acciones")
	public List<Accion> detalle() {
		return subirAntebrazoService.listarAcciones();
		
	}
	
	
	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@PostMapping("/enviar")
	@ResponseStatus(HttpStatus.OK)
	public Accion guardar(@RequestBody SubirAntebrazo subirAntebrazo) {
		Accion accion = new Accion(subirAntebrazo);
		Accion respuesta = enviar(accion);
		if ("true".equals(respuesta.getMovimiento()) || "full".equals(respuesta.getMovimiento())) {
			subirAntebrazoService.save(subirAntebrazo);
		}
		return respuesta;
	}
	
	@PostMapping
	public Accion enviar(@RequestBody Accion accion) {
		Accion respuesta = subirAntebrazoService.enviar(accion);
		return respuesta;
	}

	public Accion metodoAlternativo(@RequestBody SubirAntebrazo subirAntebrazo) {
		Accion accionError = new Accion();
		accionError.setId(subirAntebrazo.getId());
		accionError.setFecha(subirAntebrazo.getFecha());
		accionError.setMovimiento("Error: El antebrazo no pudo subir.");
		SubirAntebrazo subirAntebrazoError = new SubirAntebrazo(accionError);
		subirAntebrazoService.save(subirAntebrazoError);
		return accionError;
	}
	
}
