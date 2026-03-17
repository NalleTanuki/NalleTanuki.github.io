package com.ipartek.servicio;

import java.util.List;


import com.ipartek.modelo.Dificultad;

public interface DificultadServicio {
	List<Dificultad> obtenerTodasLasDificultades();
	Dificultad obtenerDificultadPorID(Integer id);
}
