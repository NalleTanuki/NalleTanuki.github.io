package com.ipartek.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.modelo.Pais;


@Repository
public interface PaisRepositorio extends JpaRepository<Pais, Integer> {
	// todo este repositorio ya tiene las operaciones basicas
}
