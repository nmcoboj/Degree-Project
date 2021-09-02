package com.titulacion.springboot.app.moverderecha.models.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.titulacion.springboot.app.moverderecha.models.dao.MoverDerechaDao;
import com.titulacion.springboot.app.moverderecha.models.entity.Accion;
import com.titulacion.springboot.app.moverderecha.models.entity.MoverDerecha;

@Service("serviceRestTemplate")
public class MoverDerechaServiceImpl implements IMoverDerechaService {


	@Autowired
	private RestTemplate clienteRest;

	@Autowired
	private MoverDerechaDao moverDerechaDao;

	@Override
	public List<MoverDerecha> findAll() {
		return (List<MoverDerecha>) moverDerechaDao.findAll();
	}

	@Override
	@Transactional
	public MoverDerecha save(MoverDerecha moverDerecha) {
		return moverDerechaDao.save(moverDerecha);

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