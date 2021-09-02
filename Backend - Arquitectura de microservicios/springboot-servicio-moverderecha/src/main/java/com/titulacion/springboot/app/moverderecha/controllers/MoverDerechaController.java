package com.titulacion.springboot.app.moverderecha.controllers;


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
import com.titulacion.springboot.app.moverderecha.models.entity.Accion;
import com.titulacion.springboot.app.moverderecha.models.entity.MoverDerecha;
import com.titulacion.springboot.app.moverderecha.models.service.IMoverDerechaService;

@CrossOrigin
@RefreshScope
@RestController
public class MoverDerechaController {
	
	
	Accion accion = new Accion();
	@Autowired
	@Qualifier("serviceRestTemplate")
	private IMoverDerechaService moverDerechaService;
	

	@GetMapping("/lista_local")
	public List<MoverDerecha> detalleLocal(){
		return moverDerechaService.findAll();
	}
	
	@GetMapping("/listar_acciones")
	public List<Accion> detalle() {
		return moverDerechaService.listarAccionesMotorIzquierdo();
		
	}
	
//	@HystrixCommand(fallbackMethod = "metodoAlternativoDerecho")
//	@PostMapping("/enviar")
//	@ResponseStatus(HttpStatus.OK)
//	public Accion guardar(@RequestBody MoverAtras moverAtras) {
//		Accion accion = new Accion(moverAtras);
//		Accion respuesta_motorderecho = enviar_motorderecho(accion);
//		Accion respuesta_izquierdo = enviar_motorizquierdo(accion);
//		if ("true".equals(respuesta_motorderecho.getMovimiento()) || "full".equals(respuesta_motorderecho.getMovimiento())) {
//			moverAtrasService.save(moverAtras);
//		}
//		return respuesta_motorderecho;
//	}
	
	
	@HystrixCommand(fallbackMethod = "metodoAlternativoDerecho")
	@PostMapping("/enviar_derecho")
	@ResponseStatus(HttpStatus.OK)
	public Accion guardar_derecho(@RequestBody MoverDerecha moverDerecha) {
		Accion accion = new Accion(moverDerecha);
		Accion respuesta_motorderecho = enviar_motorderecho(accion);
		if ("true".equals(respuesta_motorderecho.getMovimiento())) {
			moverDerechaService.save(moverDerecha);
		}
		return respuesta_motorderecho;
	}
	
	@HystrixCommand(fallbackMethod = "metodoAlternativoIzquierdo")
	@PostMapping("/enviar_izquierdo")
	@ResponseStatus(HttpStatus.OK)
	public Accion guardar_izquierdo(@RequestBody MoverDerecha moverDerecha) {
		Accion accion = new Accion(moverDerecha);
		Accion respuesta_motorizquierdo = enviar_motorizquierdo(accion);
		if ("true".equals(respuesta_motorizquierdo.getMovimiento())) {
			moverDerechaService.save(moverDerecha);
		}
		return respuesta_motorizquierdo;
	}
	
	
	public Accion enviar_motorizquierdo(@RequestBody Accion accion) {
		Accion respuesta = moverDerechaService.enviar_motorizquierdo(accion);
		return respuesta;
	}
	
	public Accion enviar_motorderecho(@RequestBody Accion accion) {
		Accion respuesta = moverDerechaService.enviar_motorderecho(accion);
		return respuesta;
	}
	
	public Accion metodoAlternativoIzquierdo(@RequestBody MoverDerecha moverDerecha) {
		Accion accionError = new Accion();
		accionError.setId(moverDerecha.getId());
		accionError.setFecha(moverDerecha.getFecha());
		accionError.setMovimiento("Error: El motor izquierdo del robot no pudo moverse atras.");
		MoverDerecha moverAtrasIzquierdoError = new MoverDerecha(accionError);
		moverDerechaService.save(moverAtrasIzquierdoError);
		enviar_motorIzquierdoFallback(moverDerecha);
		return accionError;
	}
	
	public Accion metodoAlternativoDerecho(@RequestBody MoverDerecha moverDerecha) {
		Accion accionError = new Accion();
		accionError.setId(moverDerecha.getId());
		accionError.setFecha(moverDerecha.getFecha());
		accionError.setMovimiento("Error: El motor derecho del robot no pudo moverse atras.");
		MoverDerecha moverAtrasDerechoError = new MoverDerecha(accionError);
		moverDerechaService.save(moverAtrasDerechoError);
		enviar_motorDerechoFallback(moverDerecha);
		return accionError;
	}
	
	public void enviar_motorDerechoFallback(@RequestBody MoverDerecha moverDerecha){
		Accion accionFallback = new Accion();
		accionFallback.setId(moverDerecha.getId());
		accionFallback.setFecha(moverDerecha.getFecha());
		if ("Derecha0".equals(moverDerecha.getMovimiento())){
			accionFallback.setMovimiento("Izquierda0");
		}
		else if ("Derecha50".equals(moverDerecha.getMovimiento())) {
			accionFallback.setMovimiento("Izquierda50");
		}
		else {
			accionFallback.setMovimiento("Izquierda100");
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		enviar_motorizquierdo(accionFallback);
		//MoverAtras moverAtrasDerechoError = new MoverAtras(accionFallback);
		//moverAtrasService.save(moverAtrasDerechoError);
	}
	
	public void enviar_motorIzquierdoFallback(@RequestBody MoverDerecha moverDerecha){
		Accion accionFallback = new Accion();
		accionFallback.setId(moverDerecha.getId());
		accionFallback.setFecha(moverDerecha.getFecha());
		if ("Derecha0".equals(moverDerecha.getMovimiento())){
			accionFallback.setMovimiento("Izquierda0");
		}
		else if ("Derecha50".equals(moverDerecha.getMovimiento())) {
			accionFallback.setMovimiento("Izquierda50");
		}
		else {
			accionFallback.setMovimiento("Izquierda100");
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		enviar_motorderecho(accionFallback);
		//MoverAtras moverAtrasDerechoError = new MoverAtras(accionFallback);
		//moverAtrasService.save(moverAtrasDerechoError);
	}
	
}
