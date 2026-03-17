package com.ipartek.modelo;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="paises")
public class Pais {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="nombre", length = 50, nullable = false)
	private String nombre;

	
	public Pais() {
		super();
		this.id = 0;
		this.nombre = "";
	}
	
	public Pais(int id, String nombre) {
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
		return "Pais [id=" + id + ", nombre=" + nombre + "]";
	}

	
	
	@Override
	public int hashCode() {
		return Objects.hash(id, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pais other = (Pais) obj;
		return id == other.id && Objects.equals(nombre, other.nombre);
	}
}
