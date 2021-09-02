package com.titulacion.springboot.app.cerrarpinza.models.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.titulacion.springboot.app.cerrarpinza.models.dao.CerrarPinzaDao;
import com.titulacion.springboot.app.cerrarpinza.models.entity.CerrarPinza;
import com.titulacion.springboot.app.cerrarpinza.models.entity.Accion;

@Service("serviceRestTemplate")
public class CerrarPinzaServiceImpl implements ICerrarPinzaService {


	@Autowired
	private RestTemplate clienteRest;

	@Autowired
	private CerrarPinzaDao cerrarPinzaDao;

	@Override
	public List<CerrarPinza> findAll() {
		return (List<CerrarPinza>) cerrarPinzaDao.findAll();
	}

	@Override
	@Transactional
	public CerrarPinza save(CerrarPinza cerrarPinza) {
		return cerrarPinzaDao.save(cerrarPinza);

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