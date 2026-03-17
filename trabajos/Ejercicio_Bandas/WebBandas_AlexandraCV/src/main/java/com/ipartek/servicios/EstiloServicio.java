package com.ipartek.servicios;

import java.util.List;

import com.ipartek.modelo.Estilo;

public interface EstiloServicio {
	//public ... aunqe no hace falta ponerlo
	List<Estilo> obtenerTodosLosEstilos();
	Estilo obtenerEstiloPorID(Integer id);
	Estilo insertarEstilo(Estilo estilo);
	Estilo modificarEstilo(Estilo estilo);
	Boolean borrarEstilo(Integer id);
}
