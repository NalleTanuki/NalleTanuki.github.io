package com.ipartek.servicios;


import java.util.List;

import com.ipartek.modelo.Rol;


public interface RolServicio {

	List<Rol> obtenerTodosLosRoles();
	
	Rol obtenerRolPorID(int id);
}
