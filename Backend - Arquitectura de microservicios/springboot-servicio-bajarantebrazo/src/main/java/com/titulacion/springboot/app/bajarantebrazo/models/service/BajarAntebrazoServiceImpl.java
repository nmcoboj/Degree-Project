package com.titulacion.springboot.app.bajarantebrazo.models.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.titulacion.springboot.app.bajarantebrazo.models.dao.BajarAntebrazoDao;
import com.titulacion.springboot.app.bajarantebrazo.models.entity.Accion;
import com.titulacion.springboot.app.bajarantebrazo.models.entity.BajarAntebrazo;

@Service("serviceRestTemplate")
public class BajarAntebrazoServiceImpl implements IBajarAntebrazoService {


	@Autowired
	private RestTemplate clienteRest;

	@Autowired
	private BajarAntebrazoDao bajarAntebrazoDao;

	@Override
	public List<BajarAntebrazo> findAll() {
		return (List<BajarAntebrazo>) bajarAntebrazoDao.findAll();
	}

	@Override
	@Transactional
	public BajarAntebrazo save(BajarAntebrazo bajarAntebrazo) {
		return bajarAntebrazoDao.save(bajarAntebrazo);

	}

	@Override
	public Accion enviar(Accion accion) {
		HttpEntity<Accion> body = new HttpEntity<Accion>(accion);
		ResponseEntity<Accion> response = clienteRest.exchange("http://servicio-motorantebrazo/enviar", HttpMethod.POST,
				body, Accion.class);
		return response.getBody();
	}

	
	@Override
	public List<Accion> listarAcciones() {
		List<Accion> acciones = Arrays
				.asList(clienteRest.getForObject("http://servicio-motorantebrazo/lista_local", Accion[].class));
		return acciones;
	}

}