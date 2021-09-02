package com.titulacion.springboot.app.apagarluces.models.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.titulacion.springboot.app.apagarluces.models.dao.ApagarLucesDao;
import com.titulacion.springboot.app.apagarluces.models.entity.ApagarLuces;
import com.titulacion.springboot.app.apagarluces.models.entity.Accion;

@Service("serviceRestTemplate")
public class ApagarLucesServiceImpl implements IApagarLucesService {

	@Autowired
	private RestTemplate clienteRest;

	@Autowired
	private ApagarLucesDao apagarLucesDao;

	@Override
	public List<ApagarLuces> findAll() {
		return (List<ApagarLuces>) apagarLucesDao.findAll();
	}

	@Override
	@Transactional
	public ApagarLuces save(ApagarLuces apagarLuces) {
		return apagarLucesDao.save(apagarLuces);

	}

	@Override
	public Accion enviar(Accion accion) {
		HttpEntity<Accion> body = new HttpEntity<Accion>(accion);
		ResponseEntity<Accion> response = clienteRest.exchange("http://servicio-luces/enviar", HttpMethod.POST, body,
				Accion.class);
		return response.getBody();
	}

	@Override
	public List<Accion> listarAcciones() {
		List<Accion> acciones = Arrays
				.asList(clienteRest.getForObject("http://servicio-luces/lista_local", Accion[].class));
		return acciones;
	}

}