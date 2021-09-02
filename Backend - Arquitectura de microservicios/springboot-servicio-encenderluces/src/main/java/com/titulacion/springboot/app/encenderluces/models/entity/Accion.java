package com.titulacion.springboot.app.encenderluces.models.entity;


public class Accion {

	private Long id;
	private String accion;
	private String fecha;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public Accion(EncenderLuces encenderLuces) {
	
		this.accion=encenderLuces.getAccion();
		this.fecha=encenderLuces.getFecha();
	
	}
	
	public Accion() {
		
	
	}
	
	
}
