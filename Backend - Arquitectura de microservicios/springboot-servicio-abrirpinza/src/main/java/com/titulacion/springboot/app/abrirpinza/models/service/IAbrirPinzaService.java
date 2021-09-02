package com.titulacion.springboot.app.abrirpinza.models.service;

import java.util.List;

import com.titulacion.springboot.app.abrirpinza.models.entity.Accion;
import com.titulacion.springboot.app.abrirpinza.models.entity.AbrirPinza;

public interface IAbrirPinzaService {

	public List<AbrirPinza> findAll();
	
	public List<Accion> listarAcciones();
	
	public AbrirPinza save(AbrirPinza abrirPinza);
	
	public Accion enviar(Accion accion);


}
