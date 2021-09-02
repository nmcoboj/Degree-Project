package com.titulacion.springboot.app.manoderecha.models.service;

import java.util.List;

import com.titulacion.springboot.app.manoderecha.models.entity.Accion;
import com.titulacion.springboot.app.manoderecha.models.entity.ManoDerecha;

public interface IManoDerechaService {

	public List<ManoDerecha> findAll();
	
	public List<Accion> listarAcciones();
	
	public ManoDerecha save(ManoDerecha manoDerecha);
	
	public Accion enviar(Accion accion);


}
