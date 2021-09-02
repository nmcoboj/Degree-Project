package com.titulacion.springboot.app.cerrarpinza.controllers;


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
import com.titulacion.springboot.app.cerrarpinza.models.entity.CerrarPinza;
import com.titulacion.springboot.app.cerrarpinza.models.entity.Accion;
import com.titulacion.springboot.app.cerrarpinza.models.service.ICerrarPinzaService;

@CrossOrigin
@RefreshScope
@RestController
public class CerrarPinzaController {
	
	Accion accion = new Accion();
	@Autowired
	@Qualifier("serviceRestTemplate")
	private ICerrarPinzaService cerrarPinzaService;
	

	@GetMapping("/lista_local")
	public List<CerrarPinza> detalleLocal(){
		return cerrarPinzaService.findAll();
	}
	
	@GetMapping("/listar_acciones")
	public List<Accion> detalle() {
		return cerrarPinzaService.listarAcciones();
		
	}
	
	
	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@PostMapping("/enviar")
	@ResponseStatus(HttpStatus.OK)
	public Accion guardar(@RequestBody CerrarPinza cerrarPinza) {
		Accion accion = new Accion(cerrarPinza);
		Accion respuesta = enviar(accion);
		if ("true".equals(respuesta.getMovimiento()) || "full".equals(respuesta.getMovimiento())) {
			cerrarPinzaService.save(cerrarPinza);
		}
		return respuesta;
	}
	
	@PostMapping
	public Accion enviar(@RequestBody Accion accion) {
		Accion respuesta = cerrarPinzaService.enviar(accion);
		return respuesta;
	}

	public Accion metodoAlternativo(@RequestBody CerrarPinza cerrarPinza) {
		Accion accionError = new Accion();
		accionError.setId(cerrarPinza.getId());
		accionError.setFecha(cerrarPinza.getFecha());
		accionError.setMovimiento("Error: La pinza no pudo cerrarse.");
		CerrarPinza cerrarPinzaError = new CerrarPinza(accionError);
		cerrarPinzaService.save(cerrarPinzaError);
		return accionError;
	}
	
}
