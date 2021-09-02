package com.titulacion.springboot.app.apagarluces.models.service;

import java.util.List;

import com.titulacion.springboot.app.apagarluces.models.entity.ApagarLuces;
import com.titulacion.springboot.app.apagarluces.models.entity.Accion;

public interface IApagarLucesService {

	public List<ApagarLuces> findAll();

	public List<Accion> listarAcciones();

	public ApagarLuces save(ApagarLuces apagarLuces);

	public Accion enviar(Accion accion);

}
