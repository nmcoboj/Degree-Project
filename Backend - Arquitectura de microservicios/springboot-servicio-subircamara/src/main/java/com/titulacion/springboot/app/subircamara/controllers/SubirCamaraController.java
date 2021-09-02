package com.titulacion.springboot.app.subircamara.controllers;


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
import com.titulacion.springboot.app.subircamara.models.entity.Accion;
import com.titulacion.springboot.app.subircamara.models.entity.SubirCamara;
import com.titulacion.springboot.app.subircamara.models.service.ISubirCamaraService;

@CrossOrigin
@RefreshScope
@RestController
public class SubirCamaraController {
	
	Accion accion = new Accion();
	@Autowired
	@Qualifier("serviceRestTemplate")
	private ISubirCamaraService subirCamaraService;
	

	@GetMapping("/lista_local")
	public List<SubirCamara> detalleLocal(){
		return subirCamaraService.findAll();
	}
	
	@GetMapping("/listar_acciones")
	public List<Accion> detalle() {
		return subirCamaraService.listarAcciones();
		
	}
	
	
	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@PostMapping("/enviar")
	@ResponseStatus(HttpStatus.OK)
	public Accion guardar(@RequestBody SubirCamara subirCamara) {
		Accion accion = new Accion(subirCamara);
		Accion respuesta = enviar(accion);
		if ("true".equals(respuesta.getMovimiento()) || "full".equals(respuesta.getMovimiento())) {
			subirCamaraService.save(subirCamara);
		}
		return respuesta;
	}
	
	@PostMapping
	public Accion enviar(@RequestBody Accion accion) {
		Accion respuesta = subirCamaraService.enviar(accion);
		return respuesta;
	}

	public Accion metodoAlternativo(@RequestBody SubirCamara subirCamara) {
		Accion accionError = new Accion();
		accionError.setId(subirCamara.getId());
		accionError.setFecha(subirCamara.getFecha());
		accionError.setMovimiento("Error: La cámara no pudo subir.");
		SubirCamara subirCamaraError = new SubirCamara(accionError);
		subirCamaraService.save(subirCamaraError);
		return accionError;
	}
	
}
