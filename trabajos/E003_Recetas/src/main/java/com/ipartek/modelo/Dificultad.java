package com.ipartek.modelo;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="dificultades")
public class Dificultad {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="nombre", nullable = false, length = 20, unique=true)
	private String nombre;

	
	
	
	public Dificultad() {
		super();
		this.id = 0;
		this.nombre = "";
	}
	
	public Dificultad(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	
	
	
	
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
	
	
	

	@Override
	public String toString() {
		return "Dificultad [id=" + id + ", nombre=" + nombre + "]";
	}
}
