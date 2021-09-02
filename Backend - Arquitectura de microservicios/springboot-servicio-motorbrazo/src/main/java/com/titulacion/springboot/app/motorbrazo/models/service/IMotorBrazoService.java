package com.titulacion.springboot.app.motorbrazo.models.service;

import java.util.List;

import com.titulacion.springboot.app.motorbrazo.models.entity.MotorBrazo;

public interface IMotorBrazoService {

	public List<MotorBrazo> findAll();
	
	public MotorBrazo save(MotorBrazo motorBrazo);
	
	public MotorBrazo enviar(MotorBrazo motorBrazo);
}
