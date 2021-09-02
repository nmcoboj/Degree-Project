package com.titulacion.springboot.app.manoizquierda.models.service;

import java.util.List;

import com.titulacion.springboot.app.manoizquierda.models.entity.Accion;
import com.titulacion.springboot.app.manoizquierda.models.entity.ManoIzquierda;

public interface IManoIzquierdaService {

	public List<ManoIzquierda> findAll();
	
	public List<Accion> listarAcciones();
	
	public ManoIzquierda save(ManoIzquierda manoIzquierda);
	
	public Accion enviar(Accion accion);


}
