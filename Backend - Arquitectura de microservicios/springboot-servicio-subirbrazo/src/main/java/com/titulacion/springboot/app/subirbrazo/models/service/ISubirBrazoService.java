package com.titulacion.springboot.app.subirbrazo.models.service;

import java.util.List;

import com.titulacion.springboot.app.subirbrazo.models.entity.Accion;
import com.titulacion.springboot.app.subirbrazo.models.entity.SubirBrazo;

public interface ISubirBrazoService {

	public List<SubirBrazo> findAll();
	
	public List<Accion> listarAcciones();
	
	public SubirBrazo save(SubirBrazo subirBrazo);
	
	public Accion enviar(Accion accion);


}
