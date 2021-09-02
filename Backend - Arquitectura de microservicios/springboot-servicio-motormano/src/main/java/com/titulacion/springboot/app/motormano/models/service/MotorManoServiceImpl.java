package com.titulacion.springboot.app.motormano.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.titulacion.springboot.app.motormano.models.dao.MotorManoDao;
import com.titulacion.springboot.app.motormano.models.entity.MotorMano;

@Service("serviceRestTemplate")
public class MotorManoServiceImpl implements IMotorManoService{
	
	
	@Autowired
	private RestTemplate clienteRest;
	
	@Autowired
	private MotorManoDao motorManoDao;
	
	@Override
	public List<MotorMano> findAll() {
		return (List<MotorMano>)  motorManoDao.findAll();
	}
	
	@Override
	@Transactional
	public MotorMano save(MotorMano motorMano) {
		return motorManoDao.save(motorMano);
	}

	@Override
	public MotorMano enviar(MotorMano motorMano) {
		MotorMano response = clienteRest.postForObject("http://192.168.1.140:5000/api/motormano", motorMano, MotorMano.class);
		return response;
	}
	
}
