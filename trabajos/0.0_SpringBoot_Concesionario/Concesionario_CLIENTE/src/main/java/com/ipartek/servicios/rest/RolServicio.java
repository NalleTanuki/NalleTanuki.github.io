package com.ipartek.servicios.rest;

import java.util.List;

import com.ipartek.pojos.Rol;

public interface RolServicio {

	List<Rol> obtenerTodosLosRoles(String token);

	Rol obtenerRolPorID(String token, int id);

}
