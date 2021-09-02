package com.titulacion.springboot.app.cambiarcolorluces.models.service;

import java.util.List;

import com.titulacion.springboot.app.cambiarcolorluces.models.entity.Accion;
import com.titulacion.springboot.app.cambiarcolorluces.models.entity.CambiarColorLuces;

public interface ICambiarColorLucesService {

	public List<CambiarColorLuces> findAll();
	
	public List<Accion> listarAcciones();
	
	public CambiarColorLuces save(CambiarColorLuces cambiarColorLuces);
	
	public Accion enviar(Accion accion);


}
