package com.titulacion.springboot.app.moverizquierda.models.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.titulacion.springboot.app.moverizquierda.models.dao.MoverIzquierdaDao;
import com.titulacion.springboot.app.moverizquierda.models.entity.Accion;
import com.titulacion.springboot.app.moverizquierda.models.entity.MoverIzquierda;

@Service("serviceRestTemplate")
public class MoverIzquierdaServiceImpl implements IMoverIzquierdaService {


	@Autowired
	private RestTemplate clienteRest;

	@Autowired
	private MoverIzquierdaDao moverIzquierdaDao;

	@Override
	public List<MoverIzquierda> findAll() {
		return (List<MoverIzquierda>) moverIzquierdaDao.findAll();
	}

	@Override
	@Transactional
	public MoverIzquierda save(MoverIzquierda moverIzquierda) {
		return moverIzquierdaDao.save(moverIzquierda);

	}

	@Override
	public Accion enviar_motorizquierdo(Accion accion) {
		HttpEntity<Accion> body = new HttpEntity<Accion>(accion);
		ResponseEntity<Accion> response = clienteRest.exchange("http://servicio-motorizquierdo/enviar", HttpMethod.POST,
				body, Accion.class);
		return response.getBody();
	}

	@Override
	public Accion enviar_motorderecho(Accion accion) {
		HttpEntity<Accion> body = new HttpEntity<Accion>(accion);
		ResponseEntity<Accion> response = clienteRest.exchange("http://servicio-motorderecho/enviar", HttpMethod.POST,
				body, Accion.class);
		return response.getBody();
	}
	
	@Override
	public List<Accion> listarAccionesMotorIzquierdo() {
		List<Accion> acciones = Arrays
				.asList(clienteRest.getForObject("http://servicio-motorizquierdo/lista_local", Accion[].class));
		return acciones;
	}
	
	@Override
	public List<Accion> listarAccionesMotorDerecho() {
		List<Accion> acciones = Arrays
				.asList(clienteRest.getForObject("http://servicio-motorderecho/lista_local", Accion[].class));
		return acciones;
	}

}