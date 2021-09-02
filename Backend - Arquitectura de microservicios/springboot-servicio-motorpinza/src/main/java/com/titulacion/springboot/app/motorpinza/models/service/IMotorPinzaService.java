package com.titulacion.springboot.app.motorpinza.models.service;

import java.util.List;

import com.titulacion.springboot.app.motorpinza.models.entity.MotorPinza;

public interface IMotorPinzaService {

	public List<MotorPinza> findAll();
	
	public MotorPinza save(MotorPinza motorPinza);
	
	public MotorPinza enviar(MotorPinza motorPinza);
}
