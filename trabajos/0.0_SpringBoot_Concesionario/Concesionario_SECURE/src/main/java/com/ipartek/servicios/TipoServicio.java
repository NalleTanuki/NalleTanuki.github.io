package com.ipartek.servicios;



import java.util.List;


import com.ipartek.modelo.Tipo;


public interface TipoServicio {

	List<Tipo> obtenerTodosTipo();
	Tipo insertarTipo(Tipo tipo);
	Tipo obtenerTipoPorId(Integer id);
	Tipo modificarTipo(Tipo tipo);
	Boolean borrarTipo(Integer id);
	
}
