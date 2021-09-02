package com.titulacion.springboot.app.subircamara.models.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.titulacion.springboot.app.subircamara.models.dao.SubirCamaraDao;
import com.titulacion.springboot.app.subircamara.models.entity.Accion;
import com.titulacion.springboot.app.subircamara.models.entity.SubirCamara;

@Service("serviceRestTemplate")
public class SubirCamaraServiceImpl implements ISubirCamaraService {


	@Autowired
	private RestTemplate clienteRest;

	@Autowired
	private SubirCamaraDao subirCamaraDao;

	@Override
	public List<SubirCamara> findAll() {
		return (List<SubirCamara>) subirCamaraDao.findAll();
	}

	@Override
	@Transactional
	public SubirCamara save(SubirCamara subirCamara) {
		return subirCamaraDao.save(subirCamara);

	}

	@Override
	public Accion enviar(Accion accion) {
		HttpEntity<Accion> body = new HttpEntity<Accion>(accion);
		ResponseEntity<Accion> response = clienteRest.exchange("http://servicio-motorcamara/enviar", HttpMethod.POST,
				body, Accion.class);
		return response.getBody();
	}

	
	@Override
	public List<Accion> listarAcciones() {
		List<Accion> acciones = Arrays
				.asList(clienteRest.getForObject("http://servicio-motorcamara/lista_local", Accion[].class));
		return acciones;
	}

}