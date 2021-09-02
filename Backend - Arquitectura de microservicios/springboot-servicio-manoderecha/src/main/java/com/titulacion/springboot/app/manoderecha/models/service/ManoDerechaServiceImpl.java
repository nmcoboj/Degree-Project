package com.titulacion.springboot.app.manoderecha.models.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.titulacion.springboot.app.manoderecha.models.dao.ManoDerechaDao;
import com.titulacion.springboot.app.manoderecha.models.entity.Accion;
import com.titulacion.springboot.app.manoderecha.models.entity.ManoDerecha;

@Service("serviceRestTemplate")
public class ManoDerechaServiceImpl implements IManoDerechaService {


	@Autowired
	private RestTemplate clienteRest;

	@Autowired
	private ManoDerechaDao manoDerechaDao;

	@Override
	public List<ManoDerecha> findAll() {
		return (List<ManoDerecha>) manoDerechaDao.findAll();
	}

	@Override
	@Transactional
	public ManoDerecha save(ManoDerecha manoDerecha) {
		return manoDerechaDao.save(manoDerecha);

	}

	@Override
	public Accion enviar(Accion accion) {
		HttpEntity<Accion> body = new HttpEntity<Accion>(accion);
		ResponseEntity<Accion> response = clienteRest.exchange("http://servicio-motormano/enviar", HttpMethod.POST,
				body, Accion.class);
		return response.getBody();
	}

	
	@Override
	public List<Accion> listarAcciones() {
		List<Accion> acciones = Arrays
				.asList(clienteRest.getForObject("http://servicio-motormano/lista_local", Accion[].class));
		return acciones;
	}

}