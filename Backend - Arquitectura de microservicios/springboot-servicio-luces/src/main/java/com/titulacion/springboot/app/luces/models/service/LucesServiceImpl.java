package com.titulacion.springboot.app.luces.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.titulacion.springboot.app.luces.models.dao.LucesDao;
import com.titulacion.springboot.app.luces.models.entity.Luces;

@Service("serviceRestTemplate")
public class LucesServiceImpl implements ILucesService{
	
	
	@Autowired
	private RestTemplate clienteRest;
	
	@Autowired
	private LucesDao lucesDao;
	
	@Override
	public List<Luces> findAll() {
		return (List<Luces>)  lucesDao.findAll();
	}
	
	@Override
	@Transactional
	public Luces save(Luces luces) {
		return lucesDao.save(luces);
	}

	@Override
	public Luces enviar(Luces luces) {
		Luces response = clienteRest.postForObject("http://192.168.1.140:5000/api/luces", luces, Luces.class);
		return response;
	}
	
}
