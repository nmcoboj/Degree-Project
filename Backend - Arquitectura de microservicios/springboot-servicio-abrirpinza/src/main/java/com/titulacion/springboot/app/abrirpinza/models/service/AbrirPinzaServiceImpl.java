package com.titulacion.springboot.app.abrirpinza.models.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.titulacion.springboot.app.abrirpinza.models.dao.AbrirPinzaDao;
import com.titulacion.springboot.app.abrirpinza.models.entity.Accion;
import com.titulacion.springboot.app.abrirpinza.models.entity.AbrirPinza;

@Service("serviceRestTemplate")
public class AbrirPinzaServiceImpl implements IAbrirPinzaService {


	@Autowired
	private RestTemplate clienteRest;

	@Autowired
	private AbrirPinzaDao abrirPinzaDao;

	@Override
	public List<AbrirPinza> findAll() {
		return (List<AbrirPinza>) abrirPinzaDao.findAll();
	}

	@Override
	@Transactional
	public AbrirPinza save(AbrirPinza abrirPinza) {
		return abrirPinzaDao.save(abrirPinza);

	}

	@Override
	public Accion enviar(Accion accion) {
		HttpEntity<Accion> body = new HttpEntity<Accion>(accion);
		ResponseEntity<Accion> response = clienteRest.exchange("http://servicio-motorpinza/enviar", HttpMethod.POST,
				body, Accion.class);
		return response.getBody();
	}

	
	@Override
	public List<Accion> listarAcciones() {
		List<Accion> acciones = Arrays
				.asList(clienteRest.getForObject("http://servicio-motorpinza/lista_local", Accion[].class));
		return acciones;
	}

}