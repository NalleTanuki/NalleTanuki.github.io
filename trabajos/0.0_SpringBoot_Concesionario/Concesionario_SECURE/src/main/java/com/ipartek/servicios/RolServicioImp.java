package com.ipartek.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.modelo.Rol;
import com.ipartek.repositorio.RolRepositorio;

@Service
public class RolServicioImp implements RolServicio {
	
	@Autowired
	private RolRepositorio rolRepo;

	@Override
	public List<Rol> obtenerTodosLosRoles() {
		try {
			return rolRepo.findAll();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Rol obtenerRolPorID(int id) {
		try {
			return rolRepo.findById(id).orElse(new Rol());
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
