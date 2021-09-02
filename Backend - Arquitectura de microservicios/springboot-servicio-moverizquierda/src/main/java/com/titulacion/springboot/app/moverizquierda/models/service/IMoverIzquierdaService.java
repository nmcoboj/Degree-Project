package com.titulacion.springboot.app.moverizquierda.models.service;

import java.util.List;

import com.titulacion.springboot.app.moverizquierda.models.entity.Accion;
import com.titulacion.springboot.app.moverizquierda.models.entity.MoverIzquierda;

public interface IMoverIzquierdaService {

	public List<MoverIzquierda> findAll();
	
	public List<Accion> listarAccionesMotorIzquierdo();
	
	public List<Accion> listarAccionesMotorDerecho();
	
	public MoverIzquierda save(MoverIzquierda moverIzquierda);
	
	public Accion enviar_motorizquierdo(Accion accion);

	public Accion enviar_motorderecho(Accion accion);
	
}
