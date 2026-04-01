package com.ipartek.pojos;

public class Rol {
	
	private int id;
	private String nombre;
	
	
//	CONSTRUCTORES
	public Rol() {
		super();
		this.id = 0;
		this.nombre = "";
	}

	public Rol(int id, String nombre) {
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
		return "Rol [id=" + id + ", nombre=" + nombre + "]";
	}
}
