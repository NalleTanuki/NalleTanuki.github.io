package com.ipartek.servicio;

import java.util.List;


import com.ipartek.modelo.Receta;

public interface RecetaServicio {
	List<Receta> obtenerTodasLasRecetas();
	Receta obtenerRecetaPorID(Integer id);
	Receta insertarReceta(Receta receta);
	Receta modificarReceta(Receta receta);
	Boolean borrarReceta(Integer id);
	
	List<Receta> buscarPorIngrediente(String texto);
}
