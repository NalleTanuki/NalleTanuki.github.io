package com.ipartek.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.modelo.Rol;

@Repository
public interface RolRepositorio extends JpaRepository<Rol, Integer> {

	Rol findByNombre(String nombre);
	
}
