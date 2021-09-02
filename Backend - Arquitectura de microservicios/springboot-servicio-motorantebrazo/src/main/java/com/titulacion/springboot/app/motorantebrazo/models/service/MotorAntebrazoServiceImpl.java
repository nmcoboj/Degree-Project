package com.titulacion.springboot.app.motorantebrazo.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.titulacion.springboot.app.motorantebrazo.models.dao.MotorAntebrazoDao;
import com.titulacion.springboot.app.motorantebrazo.models.entity.MotorAntebrazo;

@Service("serviceRestTemplate")
public class MotorAntebrazoServiceImpl implements IMotorAntebrazoService{
	
	
	@Autowired
	private RestTemplate clienteRest;
	
	@Autowired
	private MotorAntebrazoDao motorAntebrazoDao;
	
	@Override
	public List<MotorAntebrazo> findAll() {
		return (List<MotorAntebrazo>)  motorAntebrazoDao.findAll();
	}
	
	@Override
	@Transactional
	public MotorAntebrazo save(MotorAntebrazo motorAntebrazo) {
		return motorAntebrazoDao.save(motorAntebrazo);
	}

	@Override
	public MotorAntebrazo enviar(MotorAntebrazo motorAntebrazo) {
		MotorAntebrazo response = clienteRest.postForObject("http://192.168.1.140:5000/api/motorantebrazo", motorAntebrazo, MotorAntebrazo.class);
		return response;
	}
	
}
