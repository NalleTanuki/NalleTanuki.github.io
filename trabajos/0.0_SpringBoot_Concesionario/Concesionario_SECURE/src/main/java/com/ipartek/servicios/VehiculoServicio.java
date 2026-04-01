package com.ipartek.servicios;


import java.util.List;


import com.ipartek.modelo.Vehiculo;

public interface VehiculoServicio {

	List<Vehiculo> obtenerTodosVehiculo();
	Vehiculo insertarVehiculo(Vehiculo veh);
	Vehiculo obtenerVehiculoPorId(Integer id);
	Vehiculo obtenerVehiculoPorMatricula(String matricula);
	Vehiculo modificarVehiculo(Vehiculo veh);
	Boolean borrarVehiculo(Integer id);
	
}
