package com.titulacion.springboot.app.subirantebrazo.models.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.titulacion.springboot.app.subirantebrazo.models.dao.SubirAntebrazoDao;
import com.titulacion.springboot.app.subirantebrazo.models.entity.Accion;
import com.titulacion.springboot.app.subirantebrazo.models.entity.SubirAntebrazo;

@Service("serviceRestTemplate")
public class SubirAntebrazoServiceImpl implements ISubirAntebrazoService {


	@Autowired
	private RestTemplate clienteRest;

	@Autowired
	private SubirAntebrazoDao subirAntebrazoDao;

	@Override
	public List<SubirAntebrazo> findAll() {
		return (List<SubirAntebrazo>) subirAntebrazoDao.findAll();
	}

	@Override
	@Transactional
	public SubirAntebrazo save(SubirAntebrazo subirAntebrazo) {
		return subirAntebrazoDao.save(subirAntebrazo);

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