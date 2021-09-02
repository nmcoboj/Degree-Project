package com.titulacion.springboot.app.bajarcamara.controllers;


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
import com.titulacion.springboot.app.bajarcamara.models.entity.BajarCamara;
import com.titulacion.springboot.app.bajarcamara.models.entity.Accion;
import com.titulacion.springboot.app.bajarcamara.models.service.IBajarCamaraService;

@CrossOrigin
@RefreshScope
@RestController
public class BajarCamaraController {
	
	Accion accion = new Accion();
	@Autowired
	@Qualifier("serviceRestTemplate")
	private IBajarCamaraService bajarCamaraService;
	

	@GetMapping("/lista_local")
	public List<BajarCamara> detalleLocal(){
		return bajarCamaraService.findAll();
	}
	
	@GetMapping("/listar_acciones")
	public List<Accion> detalle() {
		return bajarCamaraService.listarAcciones();
		
	}
	
	
	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@PostMapping("/enviar")
	@ResponseStatus(HttpStatus.OK)
	public Accion guardar(@RequestBody BajarCamara bajarCamara) {
		Accion accion = new Accion(bajarCamara);
		Accion respuesta = enviar(accion);
		if ("true".equals(respuesta.getMovimiento()) || "full".equals(respuesta.getMovimiento())) {
			bajarCamaraService.save(bajarCamara);
		}
		return respuesta;
	}
	
	@PostMapping
	public Accion enviar(@RequestBody Accion accion) {
		Accion respuesta = bajarCamaraService.enviar(accion);
		return respuesta;
	}

	public Accion metodoAlternativo(@RequestBody BajarCamara bajarCamara) {
		Accion accionError = new Accion();
		accionError.setId(bajarCamara.getId());
		accionError.setFecha(bajarCamara.getFecha());
		accionError.setMovimiento("Error: La cámara no pudo bajar.");
		BajarCamara bajarCamaraError = new BajarCamara(accionError);
		bajarCamaraService.save(bajarCamaraError);
		return accionError;
	}
	
}
