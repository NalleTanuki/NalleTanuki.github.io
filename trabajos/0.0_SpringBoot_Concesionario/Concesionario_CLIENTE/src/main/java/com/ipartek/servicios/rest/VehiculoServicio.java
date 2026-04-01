package com.ipartek.servicios.rest;

import java.util.List;

import com.ipartek.pojos.Vehiculo;

public interface VehiculoServicio {

	List<Vehiculo> obtenerTodosVehiculo(String token);
	Vehiculo insertarVehiculo(String token, Vehiculo veh);
	Vehiculo obtenerVehiculoPorId(String token, Integer id);
	Vehiculo obtenerVehiculoPorMatricula(String token, String matricula);
	Vehiculo modificarVehiculo(String token, Vehiculo veh);
	Boolean borrarVehiculo(String token, Integer id);
	
}
