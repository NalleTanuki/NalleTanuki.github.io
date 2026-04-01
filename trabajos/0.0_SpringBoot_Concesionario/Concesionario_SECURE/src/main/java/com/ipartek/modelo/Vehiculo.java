package com.ipartek.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="vehiculos")
public class Vehiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="modelo", nullable = false, length = 50)
	private String modelo;
	
	@Column(name="matricula", unique = true, nullable = false, length = 7)
	private String matricula;
	
	@ManyToOne
	@JoinColumn(name = "fk_tipo", nullable = false)
	private Tipo tipo;
	
	@ManyToOne
	@JoinColumn(name = "fk_marca", nullable = false)
	private Marca marca;

	
//	CONSTRUCTORES
	public Vehiculo() {
		super();
		this.id = 0;
		this.modelo = "";
		this.matricula = "";
		this.tipo = new Tipo();
		this.marca = new Marca();
	}
	
	public Vehiculo(int id, String modelo, String matricula, Tipo tipo, Marca marca) {
		super();
		this.id = id;
		this.modelo = modelo;
		this.matricula = matricula;
		this.tipo = tipo;
		this.marca = marca;
	}

	
	
//	GETTERS - SETTERS
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	
//	toSTRING
	@Override
	public String toString() {
		return "Vehiculo [id=" + id + ", modelo=" + modelo + ", matricula=" + matricula + ", tipo=" + tipo + ", marca="
				+ marca + "]";
	}
}
