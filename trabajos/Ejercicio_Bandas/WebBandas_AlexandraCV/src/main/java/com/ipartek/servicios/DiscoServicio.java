package com.ipartek.servicios;

import java.util.List;

import com.ipartek.modelo.Disco;

public interface DiscoServicio {
	List<Disco> obtenerTodosLosDiscos();
	List<Disco> obtenerTodosLosDiscosOrdenadosPrecioASC();
	List<Disco> obtenerTodosLosDiscosOrdenadosPrecioDESC();
	List<Disco> obtenerTodosLosDiscosPorBanda(Integer id);
	Disco obtenerDiscoPorID(Integer id);
	Disco insertarDisco(Disco disco);
	Disco modificarDisco(Disco disco);
	Boolean borrarDisco(Integer id);
}
