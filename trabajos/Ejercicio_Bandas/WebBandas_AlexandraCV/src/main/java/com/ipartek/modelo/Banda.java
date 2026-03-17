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
@Table(name="bandas")
public class Banda {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="nombre", length = 50, nullable = false)
	private String nombre;
	
	@Column(name="numIntegrantes", nullable = false)
	private int numIntegrantes;
	
	// para los FK (pais, estilo)
	//@xxxxxxx to yyyyy -> muchos grupos (MANY) de mi tabla a 1 pais (ONE)
	@ManyToOne
	@JoinColumn(nullable=false) // para que no sea nulo
	private Pais pais;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Estilo estilo;
	
	
	public Banda() {
		super();
		this.id = 0;
		this.nombre = "";
		this.numIntegrantes = 0;
		this.pais = new Pais();      // FK
		this.estilo = new Estilo();  // FK
	}
	
	public Banda(int id, String nombre, int numIntegrantes, Pais pais, Estilo estilo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.numIntegrantes = numIntegrantes;
		this.pais = pais;
		this.estilo = estilo;
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

	public int getNumIntegrantes() {
		return numIntegrantes;
	}

	public void setNumIntegrantes(int numIntegrantes) {
		this.numIntegrantes = numIntegrantes;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Estilo getEstilo() {
		return estilo;
	}

	public void setEstilo(Estilo estilo) {
		this.estilo = estilo;
	}

	
	
	@Override
	public String toString() {
		return "Banda [id=" + id + ", nombre=" + nombre + ", numIntegrantes=" + numIntegrantes + ", pais=" + pais
				+ ", estilo=" + estilo + "]";
	}

	
	
	@Override
	public int hashCode() {
		return Objects.hash(estilo, id, nombre, numIntegrantes, pais);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Banda other = (Banda) obj;
		return Objects.equals(estilo, other.estilo) && id == other.id && Objects.equals(nombre, other.nombre)
				&& numIntegrantes == other.numIntegrantes && Objects.equals(pais, other.pais);
	}
}
