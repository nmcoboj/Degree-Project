package com.titulacion.springboot.app.motormano.models.service;

import java.util.List;

import com.titulacion.springboot.app.motormano.models.entity.MotorMano;

public interface IMotorManoService {

	public List<MotorMano> findAll();
	
	public MotorMano save(MotorMano motorMano);
	
	public MotorMano enviar(MotorMano motorMano);
}
