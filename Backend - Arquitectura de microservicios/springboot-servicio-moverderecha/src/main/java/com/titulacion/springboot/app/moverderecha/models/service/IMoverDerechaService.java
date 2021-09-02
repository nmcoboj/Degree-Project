package com.titulacion.springboot.app.moverderecha.models.service;

import java.util.List;

import com.titulacion.springboot.app.moverderecha.models.entity.Accion;
import com.titulacion.springboot.app.moverderecha.models.entity.MoverDerecha;

public interface IMoverDerechaService {

	public List<MoverDerecha> findAll();
	
	public List<Accion> listarAccionesMotorIzquierdo();
	
	public List<Accion> listarAccionesMotorDerecho();
	
	public MoverDerecha save(MoverDerecha moverDerecha);
	
	public Accion enviar_motorizquierdo(Accion accion);

	public Accion enviar_motorderecho(Accion accion);
	
}
