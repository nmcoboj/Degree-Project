package com.titulacion.springboot.app.bajarbrazo.models.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.titulacion.springboot.app.bajarbrazo.models.dao.BajarBrazoDao;
import com.titulacion.springboot.app.bajarbrazo.models.entity.Accion;
import com.titulacion.springboot.app.bajarbrazo.models.entity.BajarBrazo;

@Service("serviceRestTemplate")
public class BajarBrazoServiceImpl implements IBajarBrazoService {


	@Autowired
	private RestTemplate clienteRest;

	@Autowired
	private BajarBrazoDao bajarBrazoDao;

	@Override
	public List<BajarBrazo> findAll() {
		return (List<BajarBrazo>) bajarBrazoDao.findAll();
	}

	@Override
	@Transactional
	public BajarBrazo save(BajarBrazo bajarBrazo) {
		return bajarBrazoDao.save(bajarBrazo);

	}

	@Override
	public Accion enviar(Accion accion) {
		HttpEntity<Accion> body = new HttpEntity<Accion>(accion);
		ResponseEntity<Accion> response = clienteRest.exchange("http://servicio-motorbrazo/enviar", HttpMethod.POST,
				body, Accion.class);
		return response.getBody();
	}

	
	@Override
	public List<Accion> listarAcciones() {
		List<Accion> acciones = Arrays
				.asList(clienteRest.getForObject("http://servicio-motorbrazo/lista_local", Accion[].class));
		return acciones;
	}

}