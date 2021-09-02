package com.titulacion.springboot.app.motorderecho.models.service;

import java.util.List;

import com.titulacion.springboot.app.motorderecho.models.entity.MotorDerecho;

public interface IMotorDerechoService {

	public List<MotorDerecho> findAll();
	
	public MotorDerecho save(MotorDerecho motorDerecho);
	
	public MotorDerecho enviar(MotorDerecho motorDerecho);
}
