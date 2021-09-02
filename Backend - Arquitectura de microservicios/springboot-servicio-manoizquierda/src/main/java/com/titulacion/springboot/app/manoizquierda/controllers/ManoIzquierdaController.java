package com.titulacion.springboot.app.manoizquierda.controllers;


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
import com.titulacion.springboot.app.manoizquierda.models.entity.Accion;
import com.titulacion.springboot.app.manoizquierda.models.entity.ManoIzquierda;
import com.titulacion.springboot.app.manoizquierda.models.service.IManoIzquierdaService;

@CrossOrigin
@RefreshScope
@RestController
public class ManoIzquierdaController {
	
	Accion accion = new Accion();
	@Autowired
	@Qualifier("serviceRestTemplate")
	private IManoIzquierdaService manoIzquierdaService;
	

	@GetMapping("/lista_local")
	public List<ManoIzquierda> detalleLocal(){
		return manoIzquierdaService.findAll();
	}
	
	@GetMapping("/listar_acciones")
	public List<Accion> detalle() {
		return manoIzquierdaService.listarAcciones();
		
	}
	
	
	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@PostMapping("/enviar")
	@ResponseStatus(HttpStatus.OK)
	public Accion guardar(@RequestBody ManoIzquierda manoIzquierda) {
		Accion accion = new Accion(manoIzquierda);
		Accion respuesta = enviar(accion);
		if ("true".equals(respuesta.getMovimiento()) || "full".equals(respuesta.getMovimiento())) {
			manoIzquierdaService.save(manoIzquierda);
		}
		return respuesta;
	}
	
	@PostMapping
	public Accion enviar(@RequestBody Accion accion) {
		Accion respuesta = manoIzquierdaService.enviar(accion);
		return respuesta;
	}

	public Accion metodoAlternativo(@RequestBody ManoIzquierda manoIzquierda) {
		Accion accionError = new Accion();
		accionError.setId(manoIzquierda.getId());
		accionError.setFecha(manoIzquierda.getFecha());
		accionError.setMovimiento("Error:La mano no pudo moverse a la izquierda.");
		ManoIzquierda manoIzquierdaError = new ManoIzquierda(accionError);
		manoIzquierdaService.save(manoIzquierdaError);
		return accionError;
	}
	
}
