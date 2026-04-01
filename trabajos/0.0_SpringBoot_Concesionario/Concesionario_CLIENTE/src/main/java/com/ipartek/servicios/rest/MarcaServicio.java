package com.ipartek.servicios.rest;

import java.util.List;

import com.ipartek.pojos.Marca;

public interface MarcaServicio {

	List<Marca> obtenerTodasMarca(String token);
	Marca insertarMarca(String token, Marca marca);
	Marca obtenerMarcaPorId(String token, Integer id);
	Marca modificarMarca(String token, Marca marca);
	Boolean borrarMarca(String token, Integer id);
	
}
