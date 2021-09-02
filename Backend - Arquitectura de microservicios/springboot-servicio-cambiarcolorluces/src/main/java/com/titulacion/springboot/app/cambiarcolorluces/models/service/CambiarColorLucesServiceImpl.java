package com.titulacion.springboot.app.cambiarcolorluces.models.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.titulacion.springboot.app.cambiarcolorluces.models.dao.CambiarColorLucesDao;
import com.titulacion.springboot.app.cambiarcolorluces.models.entity.Accion;
import com.titulacion.springboot.app.cambiarcolorluces.models.entity.CambiarColorLuces;

@Service("serviceRestTemplate")
public class CambiarColorLucesServiceImpl implements ICambiarColorLucesService {


	@Autowired
	private RestTemplate clienteRest;

	@Autowired
	private CambiarColorLucesDao cambiarColorLucesDao;

	@Override
	public List<CambiarColorLuces> findAll() {
		return (List<CambiarColorLuces>) cambiarColorLucesDao.findAll();
	}

	@Override
	@Transactional
	public CambiarColorLuces save(CambiarColorLuces cambiarColorLuces) {
		return cambiarColorLucesDao.save(cambiarColorLuces);

	}

	@Override
	public Accion enviar(Accion accion) {
		HttpEntity<Accion> body = new HttpEntity<Accion>(accion);
		ResponseEntity<Accion> response = clienteRest.exchange("http://servicio-luces/enviar", HttpMethod.POST,
				body, Accion.class);
		return response.getBody();
	}

	
	@Override
	public List<Accion> listarAcciones() {
		List<Accion> acciones = Arrays
				.asList(clienteRest.getForObject("http://servicio-luces/lista_local", Accion[].class));
		return acciones;
	}

}