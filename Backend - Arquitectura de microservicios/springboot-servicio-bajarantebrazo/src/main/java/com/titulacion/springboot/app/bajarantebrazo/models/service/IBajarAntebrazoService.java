package com.titulacion.springboot.app.bajarantebrazo.models.service;

import java.util.List;

import com.titulacion.springboot.app.bajarantebrazo.models.entity.Accion;
import com.titulacion.springboot.app.bajarantebrazo.models.entity.BajarAntebrazo;

public interface IBajarAntebrazoService {

	public List<BajarAntebrazo> findAll();
	
	public List<Accion> listarAcciones();
	
	public BajarAntebrazo save(BajarAntebrazo bajarAntebrazo);
	
	public Accion enviar(Accion accion);


}
