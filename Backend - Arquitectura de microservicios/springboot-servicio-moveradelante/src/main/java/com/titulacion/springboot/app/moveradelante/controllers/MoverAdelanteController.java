package com.titulacion.springboot.app.moveradelante.controllers;


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
import com.titulacion.springboot.app.moveradelante.models.entity.Accion;
import com.titulacion.springboot.app.moveradelante.models.entity.MoverAdelante;
import com.titulacion.springboot.app.moveradelante.models.service.IMoverAdelanteService;

@CrossOrigin
@RefreshScope
@RestController
public class MoverAdelanteController {
	
	
	Accion accion = new Accion();
	@Autowired
	@Qualifier("serviceRestTemplate")
	private IMoverAdelanteService moverAdelanteService;
	

	@GetMapping("/lista_local")
	public List<MoverAdelante> detalleLocal(){
		return moverAdelanteService.findAll();
	}
	
	@GetMapping("/listar_acciones")
	public List<Accion> detalle() {
		return moverAdelanteService.listarAccionesMotorIzquierdo();
		
	}
	
	
	
	
	@HystrixCommand(fallbackMethod = "metodoAlternativoDerecho")
	@PostMapping("/enviar_derecho")
	@ResponseStatus(HttpStatus.OK)
	public Accion guardar_derecho(@RequestBody MoverAdelante moverAdelante) {
		Accion accion = new Accion(moverAdelante);
		Accion respuesta_motorderecho = enviar_motorderecho(accion);
		if ("true".equals(respuesta_motorderecho.getMovimiento()) || "Obstaculo".equals(respuesta_motorderecho.getMovimiento())) {
			moverAdelanteService.save(moverAdelante);
		}
		return respuesta_motorderecho;
	}
	
	@HystrixCommand(fallbackMethod = "metodoAlternativoIzquierdo")
	@PostMapping("/enviar_izquierdo")
	@ResponseStatus(HttpStatus.OK)
	public Accion guardar_izquierdo(@RequestBody MoverAdelante moverAdelante) {
		Accion accion = new Accion(moverAdelante);
		Accion respuesta_motorizquierdo = enviar_motorizquierdo(accion);
		if ("true".equals(respuesta_motorizquierdo.getMovimiento())  || "Obstaculo".equals(respuesta_motorizquierdo.getMovimiento())) {
			moverAdelanteService.save(moverAdelante);
		}
		return respuesta_motorizquierdo;
	}
	
	
	public Accion enviar_motorizquierdo(@RequestBody Accion accion) {
		Accion respuesta = moverAdelanteService.enviar_motorizquierdo(accion);
		return respuesta;
	}
	
	public Accion enviar_motorderecho(@RequestBody Accion accion) {
		Accion respuesta = moverAdelanteService.enviar_motorderecho(accion);
		return respuesta;
	}
	
	public Accion metodoAlternativoIzquierdo(@RequestBody MoverAdelante moverAdelante) {
		Accion accionError = new Accion();
		accionError.setId(moverAdelante.getId());
		accionError.setFecha(moverAdelante.getFecha());
		accionError.setMovimiento("Error: El motor izquierdo del robot no pudo moverse atras.");
		MoverAdelante moverAtrasIzquierdoError = new MoverAdelante(accionError);
		moverAdelanteService.save(moverAtrasIzquierdoError);
		enviar_motorIzquierdoFallback(moverAdelante);
		return accionError;
	}
	
	public Accion metodoAlternativoDerecho(@RequestBody MoverAdelante moverAdelante) {
		Accion accionError = new Accion();
		accionError.setId(moverAdelante.getId());
		accionError.setFecha(moverAdelante.getFecha());
		accionError.setMovimiento("Error: El motor derecho del robot no pudo moverse atras.");
		MoverAdelante moverAtrasDerechoError = new MoverAdelante(accionError);
		moverAdelanteService.save(moverAtrasDerechoError);
		enviar_motorDerechoFallback(moverAdelante);
		return accionError;
	}
	
	public void enviar_motorDerechoFallback(@RequestBody MoverAdelante moverAdelante){
		Accion accionFallback = new Accion();
		accionFallback.setId(moverAdelante.getId());
		accionFallback.setFecha(moverAdelante.getFecha());
		if ("Adelante0".equals(moverAdelante.getMovimiento())){
			accionFallback.setMovimiento("Atras0");
		}
		else if ("Adelante50".equals(moverAdelante.getMovimiento())) {
			accionFallback.setMovimiento("Atras50");
		}
		else {
			accionFallback.setMovimiento("Atras100");
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
	
	public void enviar_motorIzquierdoFallback(@RequestBody MoverAdelante moverAdelante){
		Accion accionFallback = new Accion();
		accionFallback.setId(moverAdelante.getId());
		accionFallback.setFecha(moverAdelante.getFecha());
		if ("Adelante0".equals(moverAdelante.getMovimiento())){
			accionFallback.setMovimiento("Atras0");
		}
		else if ("Adelante50".equals(moverAdelante.getMovimiento())) {
			accionFallback.setMovimiento("Atras50");
		}
		else {
			accionFallback.setMovimiento("Atras100");
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
