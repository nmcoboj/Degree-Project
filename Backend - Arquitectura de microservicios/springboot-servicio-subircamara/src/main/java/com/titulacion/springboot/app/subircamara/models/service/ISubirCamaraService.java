package com.titulacion.springboot.app.subircamara.models.service;

import java.util.List;

import com.titulacion.springboot.app.subircamara.models.entity.Accion;
import com.titulacion.springboot.app.subircamara.models.entity.SubirCamara;

public interface ISubirCamaraService {

	public List<SubirCamara> findAll();
	
	public List<Accion> listarAcciones();
	
	public SubirCamara save(SubirCamara subirCamara);
	
	public Accion enviar(Accion accion);


}
