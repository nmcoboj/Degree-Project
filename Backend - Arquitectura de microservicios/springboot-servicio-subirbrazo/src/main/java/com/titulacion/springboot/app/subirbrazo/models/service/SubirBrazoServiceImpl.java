package com.titulacion.springboot.app.subirbrazo.models.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.titulacion.springboot.app.subirbrazo.models.dao.SubirBrazoDao;
import com.titulacion.springboot.app.subirbrazo.models.entity.Accion;
import com.titulacion.springboot.app.subirbrazo.models.entity.SubirBrazo;

@Service("serviceRestTemplate")
public class SubirBrazoServiceImpl implements ISubirBrazoService {


	@Autowired
	private RestTemplate clienteRest;

	@Autowired
	private SubirBrazoDao subirBrazoDao;

	@Override
	public List<SubirBrazo> findAll() {
		return (List<SubirBrazo>) subirBrazoDao.findAll();
	}

	@Override
	@Transactional
	public SubirBrazo save(SubirBrazo subirBrazo) {
		return subirBrazoDao.save(subirBrazo);

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