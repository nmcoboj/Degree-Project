package com.titulacion.springboot.app.bajarbrazo.models.service;

import java.util.List;

import com.titulacion.springboot.app.bajarbrazo.models.entity.Accion;
import com.titulacion.springboot.app.bajarbrazo.models.entity.BajarBrazo;

public interface IBajarBrazoService {

	public List<BajarBrazo> findAll();
	
	public List<Accion> listarAcciones();
	
	public BajarBrazo save(BajarBrazo bajarBrazo);
	
	public Accion enviar(Accion accion);


}
