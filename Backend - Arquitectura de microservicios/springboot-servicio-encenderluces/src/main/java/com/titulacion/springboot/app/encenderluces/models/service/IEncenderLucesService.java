package com.titulacion.springboot.app.encenderluces.models.service;

import java.util.List;

import com.titulacion.springboot.app.encenderluces.models.entity.Accion;
import com.titulacion.springboot.app.encenderluces.models.entity.EncenderLuces;

public interface IEncenderLucesService {

	public List<EncenderLuces> findAll();
	
	public List<Accion> listarAcciones();
	
	public EncenderLuces save(EncenderLuces encenderLuces);
	
	public Accion enviar(Accion accion);


}
