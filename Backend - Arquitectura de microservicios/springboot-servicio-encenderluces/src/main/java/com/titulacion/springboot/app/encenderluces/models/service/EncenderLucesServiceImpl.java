package com.titulacion.springboot.app.encenderluces.models.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.titulacion.springboot.app.encenderluces.models.dao.EncenderLucesDao;
import com.titulacion.springboot.app.encenderluces.models.entity.Accion;
import com.titulacion.springboot.app.encenderluces.models.entity.EncenderLuces;

@Service("serviceRestTemplate")
public class EncenderLucesServiceImpl implements IEncenderLucesService {


	@Autowired
	private RestTemplate clienteRest;

	@Autowired
	private EncenderLucesDao encenderLucesDao;

	@Override
	public List<EncenderLuces> findAll() {
		return (List<EncenderLuces>) encenderLucesDao.findAll();
	}

	@Override
	@Transactional
	public EncenderLuces save(EncenderLuces encenderLuces) {
		return encenderLucesDao.save(encenderLuces);

	}

	@Override
	public Accion enviar(Accion accion) {
		HttpEntity<Accion> body = new HttpEntity<Accion>(accion);
		/* ResponseEntity<Accion> response = clienteRest.exchange("https://servicio-zuul-server/api/luces/enviar", HttpMethod.POST,
				body, Accion.class); */
		ResponseEntity<Accion> response = clienteRest.exchange("http://servicio-luces/enviar", HttpMethod.POST,
				body, Accion.class);
		return response.getBody();
	}

	
	@Override
	public List<Accion> listarAcciones() {
		/* List<Accion> acciones = Arrays
				.asList(clienteRest.getForObject("https://servicio-zuul-server/api/luces/lista_local", Accion[].class));*/
		List<Accion> acciones = Arrays
				.asList(clienteRest.getForObject("http://servicio-luces/lista_local", Accion[].class));
		return acciones;
	}

}