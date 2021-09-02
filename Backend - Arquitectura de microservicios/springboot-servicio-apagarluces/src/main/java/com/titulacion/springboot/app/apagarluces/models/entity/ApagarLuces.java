package com.titulacion.springboot.app.apagarluces.models.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Apagar_luces")
public class ApagarLuces {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String accion;
	private String fecha;

	public ApagarLuces() {

	}

	public ApagarLuces(Accion accion) {
		this.accion = accion.getAccion();
		this.fecha = accion.getFecha();
		this.id = accion.getId();
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
