package com.ipartek.servicio;

import java.util.List;


import com.ipartek.modelo.Ingrediente;

public interface IngredienteServicio {
	List<Ingrediente> obtenerTodosLosIngredientes();
	Ingrediente obtenerIngredientePorID(Integer id);
	Ingrediente insertarIngrediente(Ingrediente ingrediente);
	Ingrediente modificarIngrediente(Ingrediente ingrediente);
	Boolean borrarIngrediente(Integer id);
}
