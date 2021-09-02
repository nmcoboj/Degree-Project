package com.titulacion.springboot.app.subirantebrazo.models.service;

import java.util.List;

import com.titulacion.springboot.app.subirantebrazo.models.entity.Accion;
import com.titulacion.springboot.app.subirantebrazo.models.entity.SubirAntebrazo;

public interface ISubirAntebrazoService {

	public List<SubirAntebrazo> findAll();
	
	public List<Accion> listarAcciones();
	
	public SubirAntebrazo save(SubirAntebrazo subirAntebrazo);
	
	public Accion enviar(Accion accion);


}
