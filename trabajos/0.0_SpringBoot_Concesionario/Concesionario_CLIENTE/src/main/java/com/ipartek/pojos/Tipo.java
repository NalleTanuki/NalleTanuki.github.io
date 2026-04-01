package com.ipartek.pojos;

public class Tipo {

	private int id;
	private String nombre;
	
	
	
//	CONSTRUCTORES
	public Tipo() {
		super();
		this.id = 0;
		this.nombre = "";
	}
	
	public Tipo(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	

	
//	GETTERS - SETTERS
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	
//	toSTRING
	@Override
	public String toString() {
		return "Marca [id=" + id + ", nombre=" + nombre + "]";
	}
}
