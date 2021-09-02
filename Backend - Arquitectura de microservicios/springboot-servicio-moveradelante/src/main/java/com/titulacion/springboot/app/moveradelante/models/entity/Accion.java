package com.titulacion.springboot.app.moveradelante.models.entity;


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
	
	public Accion(MoverAdelante moverAdelante) {
	
		this.movimiento=moverAdelante.getMovimiento();
		this.fecha=moverAdelante.getFecha();
	
	}
	
	public Accion() {
		
	
	}
	
	
}
