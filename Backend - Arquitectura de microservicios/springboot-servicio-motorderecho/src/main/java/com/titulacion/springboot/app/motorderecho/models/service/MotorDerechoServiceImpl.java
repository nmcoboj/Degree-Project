package com.titulacion.springboot.app.motorderecho.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.titulacion.springboot.app.motorderecho.models.dao.MotorDerechoDao;
import com.titulacion.springboot.app.motorderecho.models.entity.MotorDerecho;

@Service("serviceRestTemplate")
public class MotorDerechoServiceImpl implements IMotorDerechoService{
	
	
	@Autowired
	private RestTemplate clienteRest;
	
	@Autowired
	private MotorDerechoDao motorDerechoDao;
	
	@Override
	public List<MotorDerecho> findAll() {
		return (List<MotorDerecho>)  motorDerechoDao.findAll();
	}
	
	@Override
	@Transactional
	public MotorDerecho save(MotorDerecho motorDerecho) {
		return motorDerechoDao.save(motorDerecho);
	}

	@Override
	public MotorDerecho enviar(MotorDerecho motorDerecho) {
		MotorDerecho response = clienteRest.postForObject("http://192.168.1.140:5000/api/motorderecho", motorDerecho, MotorDerecho.class);
		return response;
	}
	
}
