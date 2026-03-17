package com.ipartek.servicios;

import java.util.List;

import com.ipartek.modelo.Pais;

// Definiciones d funciones de lo qe quiero qe haga
// iran los procedimientos almacenados pero SOLO el nombre
public interface PaisServicio {
	
	//Mostrar una lista pra obtener tdos los paises
	List<Pais> obtenerTodosLosPaises();
	
	//Mostrar pais x ID
	Pais obtenerPaisPorID(Integer id);
	
	// Insertar un pais - Void es inserto
	//void insertarPais(Pais pais);    // NO devuelve nada
	Pais insertarPais(Pais pais);      // Devuelve el pais
	
	// Modificar pais
	Pais modificarPais(Pais pais);
	
	// Borrar pais - Es boolean xq borra o no borra (true/false)
	Boolean borrarPais(Integer id);
}
