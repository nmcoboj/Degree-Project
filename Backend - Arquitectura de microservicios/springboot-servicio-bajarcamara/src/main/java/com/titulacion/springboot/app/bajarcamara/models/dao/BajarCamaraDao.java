package com.titulacion.springboot.app.bajarcamara.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.titulacion.springboot.app.bajarcamara.models.entity.BajarCamara;

public interface BajarCamaraDao extends CrudRepository<BajarCamara, Long>{

}
