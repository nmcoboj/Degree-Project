package com.titulacion.springboot.app.motorbrazo.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.titulacion.springboot.app.motorbrazo.models.dao.MotorBrazoDao;
import com.titulacion.springboot.app.motorbrazo.models.entity.MotorBrazo;

@Service("serviceRestTemplate")
public class MotorBrazoServiceImpl implements IMotorBrazoService{
	
	
	@Autowired
	private RestTemplate clienteRest;
	
	@Autowired
	private MotorBrazoDao motorBrazoDao;
	
	@Override
	public List<MotorBrazo> findAll() {
		return (List<MotorBrazo>)  motorBrazoDao.findAll();
	}
	
	@Override
	@Transactional
	public MotorBrazo save(MotorBrazo motorBrazo) {
		return motorBrazoDao.save(motorBrazo);
	}

	@Override
	public MotorBrazo enviar(MotorBrazo motorBrazo) {
		MotorBrazo response = clienteRest.postForObject("http://192.168.1.140:5000/api/motorbrazo", motorBrazo, MotorBrazo.class);
		return response;
	}
	
}
