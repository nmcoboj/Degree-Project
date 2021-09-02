package com.titulacion.springboot.app.luces.models.service;

import java.util.List;

import com.titulacion.springboot.app.luces.models.entity.Luces;

public interface ILucesService {

	public List<Luces> findAll();
	
	public Luces save(Luces luces);
	
	public Luces enviar(Luces luces);
}
