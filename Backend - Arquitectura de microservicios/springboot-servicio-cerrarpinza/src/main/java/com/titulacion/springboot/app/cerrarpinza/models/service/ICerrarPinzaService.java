package com.titulacion.springboot.app.cerrarpinza.models.service;

import java.util.List;

import com.titulacion.springboot.app.cerrarpinza.models.entity.CerrarPinza;
import com.titulacion.springboot.app.cerrarpinza.models.entity.Accion;

public interface ICerrarPinzaService {

	public List<CerrarPinza> findAll();
	
	public List<Accion> listarAcciones();
	
	public CerrarPinza save(CerrarPinza cerrarPinza);
	
	public Accion enviar(Accion accion);


}
