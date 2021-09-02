package com.titulacion.springboot.app.motorizquierdo.models.service;

import java.util.List;

import com.titulacion.springboot.app.motorizquierdo.models.entity.MotorIzquierdo;

public interface IMotorIzquierdoService {

	public List<MotorIzquierdo> findAll();
	
	public MotorIzquierdo save(MotorIzquierdo motorIzquierdo);
	
	public MotorIzquierdo enviar(MotorIzquierdo motorIzquierdo);
}
