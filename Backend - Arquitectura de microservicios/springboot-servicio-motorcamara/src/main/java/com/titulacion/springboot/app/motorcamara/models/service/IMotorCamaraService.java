package com.titulacion.springboot.app.motorcamara.models.service;

import java.util.List;

import com.titulacion.springboot.app.motorcamara.models.entity.MotorCamara;

public interface IMotorCamaraService {

	public List<MotorCamara> findAll();
	
	public MotorCamara save(MotorCamara motorCamara);
	
	public MotorCamara enviar(MotorCamara motorCamara);
}
