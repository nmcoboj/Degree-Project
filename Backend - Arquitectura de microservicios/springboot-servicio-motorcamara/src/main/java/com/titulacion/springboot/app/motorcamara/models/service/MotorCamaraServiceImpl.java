package com.titulacion.springboot.app.motorcamara.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.titulacion.springboot.app.motorcamara.models.dao.MotorCamaraDao;
import com.titulacion.springboot.app.motorcamara.models.entity.MotorCamara;

@Service("serviceRestTemplate")
public class MotorCamaraServiceImpl implements IMotorCamaraService{
	
	
	@Autowired
	private RestTemplate clienteRest;
	
	@Autowired
	private MotorCamaraDao motorCamaraDao;
	
	@Override
	public List<MotorCamara> findAll() {
		return (List<MotorCamara>)  motorCamaraDao.findAll();
	}
	
	@Override
	@Transactional
	public MotorCamara save(MotorCamara motorCamara) {
		return motorCamaraDao.save(motorCamara);
	}

	@Override
	public MotorCamara enviar(MotorCamara motorCamara) {
		MotorCamara response = clienteRest.postForObject("http://192.168.1.140:5000/api/motorcamara", motorCamara, MotorCamara.class);
		return response;
	}
	
}
