package com.ipartek.servicio;

import java.util.List;

import com.ipartek.modelo.Pais;

public interface PaisServicio {
	List<Pais> obtenerTodosLosPaises();
	Pais obtenerPaisPorID(Integer id);
	Pais insertarPais(Pais pais);
	Pais modificarPais(Pais pais);
	Boolean borrarPais(Integer id);
}
