package com.titulacion.springboot.app.moveratras.models.service;

import java.util.List;

import com.titulacion.springboot.app.moveratras.models.entity.Accion;
import com.titulacion.springboot.app.moveratras.models.entity.MoverAtras;

public interface IMoverAtrasService {

	public List<MoverAtras> findAll();
	
	public List<Accion> listarAccionesMotorIzquierdo();
	
	public List<Accion> listarAccionesMotorDerecho();
	
	public MoverAtras save(MoverAtras moverAtras);
	
	public Accion enviar_motorizquierdo(Accion accion);

	public Accion enviar_motorderecho(Accion accion);
	
}
