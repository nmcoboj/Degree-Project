package com.titulacion.springboot.app.motorantebrazo.models.service;

import java.util.List;

import com.titulacion.springboot.app.motorantebrazo.models.entity.MotorAntebrazo;

public interface IMotorAntebrazoService {

	public List<MotorAntebrazo> findAll();
	
	public MotorAntebrazo save(MotorAntebrazo motorAntebrazo);
	
	public MotorAntebrazo enviar(MotorAntebrazo motorAntebrazo);
}
