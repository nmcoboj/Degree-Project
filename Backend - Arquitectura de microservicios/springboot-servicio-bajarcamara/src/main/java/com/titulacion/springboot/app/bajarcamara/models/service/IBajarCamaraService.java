package com.titulacion.springboot.app.bajarcamara.models.service;

import java.util.List;

import com.titulacion.springboot.app.bajarcamara.models.entity.BajarCamara;
import com.titulacion.springboot.app.bajarcamara.models.entity.Accion;

public interface IBajarCamaraService {

	public List<BajarCamara> findAll();
	
	public List<Accion> listarAcciones();
	
	public BajarCamara save(BajarCamara bajarCamara);
	
	public Accion enviar(Accion accion);


}
