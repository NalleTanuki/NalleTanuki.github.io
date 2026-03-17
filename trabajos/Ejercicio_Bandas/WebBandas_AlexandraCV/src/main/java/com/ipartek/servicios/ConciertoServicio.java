package com.ipartek.servicios;

import java.util.List;


import com.ipartek.modelo.Concierto;


public interface ConciertoServicio {
	List<Concierto> obtenerTodosLosConciertos();
	List<Concierto> obtenerTodosLosConciertosOrdenadosFechaASC();
	Concierto obtenerConciertoPorID(Integer id);
	Concierto insertarConcierto(Concierto concierto);
	Concierto modificarConcierto(Concierto concierto);
	Boolean borrarConcierto(Integer id);
}
