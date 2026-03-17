package com.ipartek.modelo;

import java.math.BigDecimal;
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
@Table(name="discos")
public class Disco {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="titulo", length = 100, nullable=false)
	private String titulo;
	
	@Column(name="anho", length = 4, nullable=false)
	private String anho;
	
	@Column(name="numCanciones", nullable=false)
	private int numCanciones;
	
	@Column(name="precio", nullable=false, precision = 10, scale=2)
	private BigDecimal precio;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Banda banda;

	public Disco() {
		super();
		this.id = 0;
		this.titulo = "";
		this.anho = "";
		this.numCanciones = 0;
		this.precio = BigDecimal.ZERO;
		this.banda = new Banda();
	}
	
	

	public Disco(int id, String titulo, String anho, int numCanciones, BigDecimal precio, Banda banda) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.anho = anho;
		this.numCanciones = numCanciones;
		this.precio = precio;
		this.banda = banda;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getAnho() {
		return anho;
	}

	public void setAnho(String anho) {
		this.anho = anho;
	}

	public int getNumCanciones() {
		return numCanciones;
	}

	public void setNumCanciones(int numCanciones) {
		this.numCanciones = numCanciones;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public Banda getBanda() {
		return banda;
	}

	public void setBanda(Banda banda) {
		this.banda = banda;
	}

	
	
	
	@Override
	public String toString() {
		return "Disco [id=" + id + ", titulo=" + titulo + ", anho=" + anho + ", numCanciones=" + numCanciones
				+ ", precio=" + precio + ", banda=" + banda + "]";
	}


	

	@Override
	public int hashCode() {
		return Objects.hash(anho, banda, id, numCanciones, precio, titulo);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Disco other = (Disco) obj;
		return Objects.equals(anho, other.anho) && Objects.equals(banda, other.banda) && id == other.id
				&& numCanciones == other.numCanciones && Objects.equals(precio, other.precio)
				&& Objects.equals(titulo, other.titulo);
	}
}
