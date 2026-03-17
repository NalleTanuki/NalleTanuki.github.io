package com.ipartek.modelo;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="receta_ingrediente")
public class RecetaIngrediente {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="id_ingrediente", nullable = false)
	private Ingrediente ingrediente;
	
	@Column(name="cantidad", length = 50, nullable = false)
	private String cantidad;
	
	@ManyToOne
	@JoinColumn(name="fk_receta", nullable = false)
	private Receta receta;

	
	
	
	public RecetaIngrediente() {
		super();
		this.id = 0;
		this.ingrediente = new Ingrediente();
		this.cantidad = "";
		this.receta = new Receta();
	}
	
	public RecetaIngrediente(int id, Ingrediente ingrediente, String cantidad, Receta receta) {
		super();
		this.id = id;
		this.ingrediente = ingrediente;
		this.cantidad = cantidad;
		this.receta = receta;
	}

	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public Receta getReceta() {
		return receta;
	}

	public void setReceta(Receta receta) {
		this.receta = receta;
	}

	
	
	
	@Override
	public String toString() {
		return "RecetaIngrediente [id=" + id + ", ingrediente=" + ingrediente.getNombre() + ", cantidad=" + cantidad + ", receta="
				+ receta.getNombre() + "]";
	}
}
