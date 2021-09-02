package com.titulacion.springboot.app.moverizquierda.controllers;


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
import com.titulacion.springboot.app.moverizquierda.models.entity.Accion;
import com.titulacion.springboot.app.moverizquierda.models.entity.MoverIzquierda;
import com.titulacion.springboot.app.moverizquierda.models.service.IMoverIzquierdaService;

@CrossOrigin
@RefreshScope
@RestController
public class MoverIzquierdaController {
	
	
	Accion accion = new Accion();
	@Autowired
	@Qualifier("serviceRestTemplate")
	private IMoverIzquierdaService moverIzquierdaService;
	

	@GetMapping("/lista_local")
	public List<MoverIzquierda> detalleLocal(){
		return moverIzquierdaService.findAll();
	}
	
	@GetMapping("/listar_acciones")
	public List<Accion> detalle() {
		return moverIzquierdaService.listarAccionesMotorIzquierdo();
		
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
	public Accion guardar_derecho(@RequestBody MoverIzquierda moverIzquierda) {
		Accion accion = new Accion(moverIzquierda);
		Accion respuesta_motorderecho = enviar_motorderecho(accion);
		if ("true".equals(respuesta_motorderecho.getMovimiento())) {
			moverIzquierdaService.save(moverIzquierda);
		}
		return respuesta_motorderecho;
	}
	
	@HystrixCommand(fallbackMethod = "metodoAlternativoIzquierdo")
	@PostMapping("/enviar_izquierdo")
	@ResponseStatus(HttpStatus.OK)
	public Accion guardar_izquierdo(@RequestBody MoverIzquierda moverIzquierda) {
		Accion accion = new Accion(moverIzquierda);
		Accion respuesta_motorizquierdo = enviar_motorizquierdo(accion);
		if ("true".equals(respuesta_motorizquierdo.getMovimiento())) {
			moverIzquierdaService.save(moverIzquierda);
		}
		return respuesta_motorizquierdo;
	}
	
	
	public Accion enviar_motorizquierdo(@RequestBody Accion accion) {
		Accion respuesta = moverIzquierdaService.enviar_motorizquierdo(accion);
		return respuesta;
	}
	
	public Accion enviar_motorderecho(@RequestBody Accion accion) {
		Accion respuesta = moverIzquierdaService.enviar_motorderecho(accion);
		return respuesta;
	}
	
	public Accion metodoAlternativoIzquierdo(@RequestBody MoverIzquierda moverIzquierda) {
		Accion accionError = new Accion();
		accionError.setId(moverIzquierda.getId());
		accionError.setFecha(moverIzquierda.getFecha());
		accionError.setMovimiento("Error: El motor izquierdo del robot no pudo moverse atras.");
		MoverIzquierda moverAtrasIzquierdoError = new MoverIzquierda(accionError);
		moverIzquierdaService.save(moverAtrasIzquierdoError);
		enviar_motorIzquierdoFallback(moverIzquierda);
		return accionError;
	}
	
	public Accion metodoAlternativoDerecho(@RequestBody MoverIzquierda moverIzquierda) {
		Accion accionError = new Accion();
		accionError.setId(moverIzquierda.getId());
		accionError.setFecha(moverIzquierda.getFecha());
		accionError.setMovimiento("Error: El motor derecho del robot no pudo moverse atras.");
		MoverIzquierda moverAtrasDerechoError = new MoverIzquierda(accionError);
		moverIzquierdaService.save(moverAtrasDerechoError);
		enviar_motorDerechoFallback(moverIzquierda);
		return accionError;
	}
	
	public void enviar_motorDerechoFallback(@RequestBody MoverIzquierda moverIzquierda){
		Accion accionFallback = new Accion();
		accionFallback.setId(moverIzquierda.getId());
		accionFallback.setFecha(moverIzquierda.getFecha());
		if ("Izquierda0".equals(moverIzquierda.getMovimiento())){
			accionFallback.setMovimiento("Derecha0");
		}
		else if ("Izquierda50".equals(moverIzquierda.getMovimiento())) {
			accionFallback.setMovimiento("Derecha50");
		}
		else {
			accionFallback.setMovimiento("Derecha100");
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
	
	public void enviar_motorIzquierdoFallback(@RequestBody MoverIzquierda moverIzquierda){
		Accion accionFallback = new Accion();
		accionFallback.setId(moverIzquierda.getId());
		accionFallback.setFecha(moverIzquierda.getFecha());
		if ("Izquierda0".equals(moverIzquierda.getMovimiento())){
			accionFallback.setMovimiento("Derecha0");
		}
		else if ("Izquierda50".equals(moverIzquierda.getMovimiento())) {
			accionFallback.setMovimiento("Derecha50");
		}
		else {
			accionFallback.setMovimiento("Derecha100");
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
