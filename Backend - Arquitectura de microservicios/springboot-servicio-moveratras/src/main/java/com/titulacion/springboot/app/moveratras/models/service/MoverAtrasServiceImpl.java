package com.titulacion.springboot.app.moveratras.models.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.titulacion.springboot.app.moveratras.models.dao.MoverAtrasDao;
import com.titulacion.springboot.app.moveratras.models.entity.Accion;
import com.titulacion.springboot.app.moveratras.models.entity.MoverAtras;

@Service("serviceRestTemplate")
public class MoverAtrasServiceImpl implements IMoverAtrasService {


	@Autowired
	private RestTemplate clienteRest;

	@Autowired
	private MoverAtrasDao moverAtrasDao;

	@Override
	public List<MoverAtras> findAll() {
		return (List<MoverAtras>) moverAtrasDao.findAll();
	}

	@Override
	@Transactional
	public MoverAtras save(MoverAtras moverAtras) {
		return moverAtrasDao.save(moverAtras);

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