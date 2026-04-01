package com.ipartek.servicios.rest;

import java.util.List;

import com.ipartek.pojos.Tipo;

public interface TipoServicio {

	List<Tipo> obtenerTodosTipo(String token);
	Tipo insertarTipo(String token, Tipo tipo);
	Tipo obtenerTipoPorId(String token, Integer id);
	Tipo modificarTipo(String token, Tipo tipo);
	Boolean borrarTipo(String token, Integer id);
	
}
