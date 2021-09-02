package com.titulacion.springboot.app.motorpinza.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.titulacion.springboot.app.motorpinza.models.dao.MotorPinzaDao;
import com.titulacion.springboot.app.motorpinza.models.entity.MotorPinza;

@Service("serviceRestTemplate")
public class MotorPinzaServiceImpl implements IMotorPinzaService{
	
	
	@Autowired
	private RestTemplate clienteRest;
	
	@Autowired
	private MotorPinzaDao motorPinzaDao;
	
	@Override
	public List<MotorPinza> findAll() {
		return (List<MotorPinza>)  motorPinzaDao.findAll();
	}
	
	@Override
	@Transactional
	public MotorPinza save(MotorPinza motorPinza) {
		return motorPinzaDao.save(motorPinza);
	}

	@Override
	public MotorPinza enviar(MotorPinza motorPinza) {
		MotorPinza response = clienteRest.postForObject("http://192.168.1.140:5000/api/motorpinza", motorPinza, MotorPinza.class);
		return response;
	}
	
}
