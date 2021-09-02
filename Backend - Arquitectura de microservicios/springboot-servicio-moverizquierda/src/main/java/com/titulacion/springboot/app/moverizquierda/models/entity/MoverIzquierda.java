package com.titulacion.springboot.app.moverizquierda.models.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Mover_izquierda")
public class MoverIzquierda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String movimiento;
	private String fecha;
	
	public MoverIzquierda() {
          
	}


	public MoverIzquierda(Accion accion) {
		this.movimiento=accion.getMovimiento();
		this.fecha=accion.getFecha();
		this.id =accion.getId();
	}


	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
}