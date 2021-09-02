package com.titulacion.springboot.app.manoizquierda.models.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.titulacion.springboot.app.manoizquierda.models.dao.ManoIzquierdaDao;
import com.titulacion.springboot.app.manoizquierda.models.entity.Accion;
import com.titulacion.springboot.app.manoizquierda.models.entity.ManoIzquierda;

@Service("serviceRestTemplate")
public class ManoIzquierdaServiceImpl implements IManoIzquierdaService {


	@Autowired
	private RestTemplate clienteRest;

	@Autowired
	private ManoIzquierdaDao manoIzquierdaDao;

	@Override
	public List<ManoIzquierda> findAll() {
		return (List<ManoIzquierda>) manoIzquierdaDao.findAll();
	}

	@Override
	@Transactional
	public ManoIzquierda save(ManoIzquierda manoIzquierda) {
		return manoIzquierdaDao.save(manoIzquierda);

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