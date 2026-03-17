package com.ipartek.modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="recetas")
public class Receta {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="nombre", length = 100, nullable = false)
	private String nombre;
	
	@Column(name="tiempo", length = 20, nullable = false)
	private String tiempo;
	
	@ManyToOne
	@JoinColumn(name="fk_dificultad", nullable = false)
	private Dificultad dificultad;
	
	@ManyToOne
	@JoinColumn(name = "fk_pais", nullable = false)
	private Pais pais;
	
	@OneToMany(mappedBy = "receta",
			   cascade = CascadeType.ALL, //le digo qe elimine en tabla intermedia, es decir, si borro 1 receta se borra en receta_ingrediente sus registros
			   fetch = FetchType.EAGER,
			   orphanRemoval = true)      // pero los ingredientes siguen existiendo
	private List<RecetaIngrediente> ingredientes;
	
	
	
	
	
	
	public Receta() {
		super();
		this.id = 0;
		this.nombre = "";
		this.tiempo = "";
		this.dificultad = new Dificultad();
		this.pais = new Pais();
		this.ingredientes = new ArrayList<>();
	}
	
	
	public Receta(int id, String nombre, String tiempo, Dificultad dificultad, Pais pais,
			List<RecetaIngrediente> ingredientes) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tiempo = tiempo;
		this.dificultad = dificultad;
		this.pais = pais;
		this.ingredientes = ingredientes;
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


	public String getTiempo() {
		return tiempo;
	}


	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}


	public Dificultad getDificultad() {
		return dificultad;
	}


	public void setDificultad(Dificultad dificultad) {
		this.dificultad = dificultad;
	}


	public Pais getPais() {
		return pais;
	}


	public void setPais(Pais pais) {
		this.pais = pais;
	}


	public List<RecetaIngrediente> getListaIngredientes() {
		return ingredientes;
	}


	public void setListaIngredientes(List<RecetaIngrediente> listaIngredientes) {
		this.ingredientes = listaIngredientes;
	}



	
	
	@Override
	public String toString() {
		return "Receta [id=" + id + ", nombre=" + nombre + ", tiempo=" + tiempo + ", dificultad=" + dificultad
				+ ", pais=" + pais + ", listaIngredientes=" + ingredientes + "]";
	}
}
