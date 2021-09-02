package com.titulacion.springboot.app.moveradelante.models.service;

import java.util.List;

import com.titulacion.springboot.app.moveradelante.models.entity.Accion;
import com.titulacion.springboot.app.moveradelante.models.entity.MoverAdelante;

public interface IMoverAdelanteService {

	public List<MoverAdelante> findAll();
	
	public List<Accion> listarAccionesMotorIzquierdo();
	
	public List<Accion> listarAccionesMotorDerecho();
	
	public MoverAdelante save(MoverAdelante moverAdelante);
	
	public Accion enviar_motorizquierdo(Accion accion);

	public Accion enviar_motorderecho(Accion accion);
	
}
