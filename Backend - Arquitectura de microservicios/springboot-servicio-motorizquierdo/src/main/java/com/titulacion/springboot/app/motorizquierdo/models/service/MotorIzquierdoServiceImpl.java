package com.titulacion.springboot.app.motorizquierdo.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.titulacion.springboot.app.motorizquierdo.models.dao.MotorIzquierdoDao;
import com.titulacion.springboot.app.motorizquierdo.models.entity.MotorIzquierdo;

@Service("serviceRestTemplate")
public class MotorIzquierdoServiceImpl implements IMotorIzquierdoService{
	
	
	@Autowired
	private RestTemplate clienteRest;
	
	@Autowired
	private MotorIzquierdoDao motorIzquierdoDao;
	
	@Override
	public List<MotorIzquierdo> findAll() {
		return (List<MotorIzquierdo>)  motorIzquierdoDao.findAll();
	}
	
	@Override
	@Transactional
	public MotorIzquierdo save(MotorIzquierdo motorIzquierdo) {
		return motorIzquierdoDao.save(motorIzquierdo);
	}

	@Override
	public MotorIzquierdo enviar(MotorIzquierdo motorIzquierdo) {
		MotorIzquierdo response = clienteRest.postForObject("http://192.168.1.140:5000/api/motorizquierdo", motorIzquierdo, MotorIzquierdo.class);
		return response;
	}
	
}
