package com.ipartek.modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="ingredientes")
public class Ingrediente {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="nombre", length = 50, nullable = false, unique = true)
	private String nombre;
	
	@OneToMany(mappedBy = "ingrediente",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<RecetaIngrediente> recetas;

	
	
	
	public Ingrediente() {
		super();
		this.id = 0;
		this.nombre = "";
		this.recetas = new ArrayList<>();
	}
	
	public Ingrediente(int id, String nombre, List<RecetaIngrediente> recetas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.recetas = recetas;
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
	
	public List<RecetaIngrediente> getRecetas() {
		return recetas;
	}

	public void setRecetas(List<RecetaIngrediente> recetas) {
		this.recetas = recetas;
	}

	
	
	
	@Override
	public String toString() {
		return "Ingrediente [id=" + id + ", nombre=" + nombre + "]";
	}
}
