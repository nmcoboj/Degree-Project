package com.titulacion.springboot.app.luces.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.titulacion.springboot.app.luces.models.entity.Luces;

public interface LucesDao extends CrudRepository<Luces, Long>{

}
