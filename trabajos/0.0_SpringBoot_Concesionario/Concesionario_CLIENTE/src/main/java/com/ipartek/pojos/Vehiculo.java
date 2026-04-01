package com.ipartek.pojos;

public class Vehiculo {

	private int id;
	private String modelo;
	private String matricula;
	
	private Marca marca;
	private Tipo tipo;
	
	
//	CONSTRUCTORES
	public Vehiculo() {
		super();
		this.id = 0;
		this.modelo = "";
		this.matricula = "";
		this.marca = new Marca();
		this.tipo = new Tipo();
	}
	
	public Vehiculo(int id, String modelo, String matricula, Marca marca, Tipo tipo) {
		super();
		this.id = id;
		this.modelo = modelo;
		this.matricula = matricula;
		this.marca = marca;
		this.tipo = tipo;
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
	
	public Marca getMarca() {
		return marca;
	}
	
	public void setMarca(Marca marca) {
		this.marca = marca;
	}
	
	public Tipo getTipo() {
		return tipo;
	}
	
	public void setTipo(Tipo tipo) {
		this.tipo= tipo;
	}

	
//	toSTRING
	@Override
	public String toString() {
		return "Vehiculo [id=" + id + ", modelo=" + modelo + ", matricula=" + matricula + "]";
	}
}
