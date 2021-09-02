package com.titulacion.springboot.app.manoderecha.controllers;


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
import com.titulacion.springboot.app.manoderecha.models.entity.Accion;
import com.titulacion.springboot.app.manoderecha.models.entity.ManoDerecha;
import com.titulacion.springboot.app.manoderecha.models.service.IManoDerechaService;

@CrossOrigin
@RefreshScope
@RestController
public class ManoDerechaController {
	
	Accion accion = new Accion();
	@Autowired
	@Qualifier("serviceRestTemplate")
	private IManoDerechaService manoDerechaService;
	

	@GetMapping("/lista_local")
	public List<ManoDerecha> detalleLocal(){
		return manoDerechaService.findAll();
	}
	
	@GetMapping("/listar_acciones")
	public List<Accion> detalle() {
		return manoDerechaService.listarAcciones();
		
	}
	
	
	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@PostMapping("/enviar")
	@ResponseStatus(HttpStatus.OK)
	public Accion guardar(@RequestBody ManoDerecha manoDerecha) {
		Accion accion = new Accion(manoDerecha);
		Accion respuesta = enviar(accion);
		if ("true".equals(respuesta.getMovimiento()) || "full".equals(respuesta.getMovimiento())) {
			manoDerechaService.save(manoDerecha);
		}
		return respuesta;
	}
	
	@PostMapping
	public Accion enviar(@RequestBody Accion accion) {
		Accion respuesta = manoDerechaService.enviar(accion);
		return respuesta;
	}

	public Accion metodoAlternativo(@RequestBody ManoDerecha manoDerecha) {
		Accion accionError = new Accion();
		accionError.setId(manoDerecha.getId());
		accionError.setFecha(manoDerecha.getFecha());
		accionError.setMovimiento("Error:La mano no pudo moverse a la derecha.");
		ManoDerecha manoDerechaError = new ManoDerecha(accionError);
		manoDerechaService.save(manoDerechaError);
		return accionError;
	}
	
}
