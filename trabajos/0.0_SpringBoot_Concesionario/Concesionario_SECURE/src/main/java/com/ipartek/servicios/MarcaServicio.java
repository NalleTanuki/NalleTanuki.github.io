package com.ipartek.servicios;

import java.util.List;

import com.ipartek.modelo.Marca;


public interface MarcaServicio {

	List<Marca> obtenerTodasMarca();
	Marca insertarMarca(Marca marca);
	Marca obtenerMarcaPorId(Integer id);
	Marca modificarMarca(Marca marca);
	Boolean borrarMarca(Integer id);
	
}
