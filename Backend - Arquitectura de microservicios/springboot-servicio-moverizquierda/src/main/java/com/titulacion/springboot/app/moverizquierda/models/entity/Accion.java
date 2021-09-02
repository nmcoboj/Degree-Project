package com.titulacion.springboot.app.moverizquierda.models.entity;


public class Accion {

	private Long id;
	private String movimiento;
	private String fecha;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMovimiento() {
		return movimiento;
	}
	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public Accion(MoverIzquierda moverIzquierda) {
	
		this.movimiento=moverIzquierda.getMovimiento();
		this.fecha=moverIzquierda.getFecha();
	
	}
	
	public Accion() {
		
	
	}
	
	
}
