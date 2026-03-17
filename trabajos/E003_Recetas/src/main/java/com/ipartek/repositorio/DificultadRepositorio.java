package com.ipartek.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.modelo.Dificultad;

@Repository
public interface DificultadRepositorio extends JpaRepository<Dificultad, Integer> {
	List<Dificultad> findAllByOrderByIdAsc();
}
