package com.titulacion.springboot.app.bajarcamara.models.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.titulacion.springboot.app.bajarcamara.models.dao.BajarCamaraDao;
import com.titulacion.springboot.app.bajarcamara.models.entity.BajarCamara;
import com.titulacion.springboot.app.bajarcamara.models.entity.Accion;

@Service("serviceRestTemplate")
public class BajarCamaraServiceImpl implements IBajarCamaraService {


	@Autowired
	private RestTemplate clienteRest;

	@Autowired
	private BajarCamaraDao bajarCamaraDao;

	@Override
	public List<BajarCamara> findAll() {
		return (List<BajarCamara>) bajarCamaraDao.findAll();
	}

	@Override
	@Transactional
	public BajarCamara save(BajarCamara bajarCamara) {
		return bajarCamaraDao.save(bajarCamara);

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