package com.titulacion.springboot.app.moveratras.controllers;


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
import com.titulacion.springboot.app.moveratras.models.entity.Accion;
import com.titulacion.springboot.app.moveratras.models.entity.MoverAtras;
import com.titulacion.springboot.app.moveratras.models.service.IMoverAtrasService;

@CrossOrigin
@RefreshScope
@RestController
public class MoverAtrasController {
	
	
	Accion accion = new Accion();
	@Autowired
	@Qualifier("serviceRestTemplate")
	private IMoverAtrasService moverAtrasService;
	

	@GetMapping("/lista_local")
	public List<MoverAtras> detalleLocal(){
		return moverAtrasService.findAll();
	}
	
	@GetMapping("/listar_acciones")
	public List<Accion> detalle() {
		return moverAtrasService.listarAccionesMotorIzquierdo();
		
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
	public Accion guardar_derecho(@RequestBody MoverAtras moverAtras) {
		Accion accion = new Accion(moverAtras);
		Accion respuesta_motorderecho = enviar_motorderecho(accion);
		if ("true".equals(respuesta_motorderecho.getMovimiento())) {
			moverAtrasService.save(moverAtras);
		}
		return respuesta_motorderecho;
	}
	
	@HystrixCommand(fallbackMethod = "metodoAlternativoIzquierdo")
	@PostMapping("/enviar_izquierdo")
	@ResponseStatus(HttpStatus.OK)
	public Accion guardar_izquierdo(@RequestBody MoverAtras moverAtras) {
		Accion accion = new Accion(moverAtras);
		Accion respuesta_motorizquierdo = enviar_motorizquierdo(accion);
		if ("true".equals(respuesta_motorizquierdo.getMovimiento())) {
			moverAtrasService.save(moverAtras);
		}
		return respuesta_motorizquierdo;
	}
	
	
	public Accion enviar_motorizquierdo(@RequestBody Accion accion) {
		Accion respuesta = moverAtrasService.enviar_motorizquierdo(accion);
		return respuesta;
	}
	
	public Accion enviar_motorderecho(@RequestBody Accion accion) {
		Accion respuesta = moverAtrasService.enviar_motorderecho(accion);
		return respuesta;
	}
	
	public Accion metodoAlternativoIzquierdo(@RequestBody MoverAtras moverAtras) {
		Accion accionError = new Accion();
		accionError.setId(moverAtras.getId());
		accionError.setFecha(moverAtras.getFecha());
		accionError.setMovimiento("Error: El motor izquierdo del robot no pudo moverse atras.");
		MoverAtras moverAtrasIzquierdoError = new MoverAtras(accionError);
		moverAtrasService.save(moverAtrasIzquierdoError);
		enviar_motorIzquierdoFallback(moverAtras);
		return accionError;
	}
	
	public Accion metodoAlternativoDerecho(@RequestBody MoverAtras moverAtras) {
		Accion accionError = new Accion();
		accionError.setId(moverAtras.getId());
		accionError.setFecha(moverAtras.getFecha());
		accionError.setMovimiento("Error: El motor derecho del robot no pudo moverse atras.");
		MoverAtras moverAtrasDerechoError = new MoverAtras(accionError);
		moverAtrasService.save(moverAtrasDerechoError);
		enviar_motorDerechoFallback(moverAtras);
		return accionError;
	}
	
	public void enviar_motorDerechoFallback(@RequestBody MoverAtras moverAtras){
		Accion accionFallback = new Accion();
		accionFallback.setId(moverAtras.getId());
		accionFallback.setFecha(moverAtras.getFecha());
		if ("Atras0".equals(moverAtras.getMovimiento())){
			accionFallback.setMovimiento("Adelante0");
		}
		else if ("Atras50".equals(moverAtras.getMovimiento())) {
			accionFallback.setMovimiento("Adelante50");
		}
		else {
			accionFallback.setMovimiento("Adelante100");
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
	
	public void enviar_motorIzquierdoFallback(@RequestBody MoverAtras moverAtras){
		Accion accionFallback = new Accion();
		accionFallback.setId(moverAtras.getId());
		accionFallback.setFecha(moverAtras.getFecha());
		if ("Atras0".equals(moverAtras.getMovimiento())){
			accionFallback.setMovimiento("Adelante0");
		}
		else if ("Atras50".equals(moverAtras.getMovimiento())) {
			accionFallback.setMovimiento("Adelante50");
		}
		else {
			accionFallback.setMovimiento("Adelante100");
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
