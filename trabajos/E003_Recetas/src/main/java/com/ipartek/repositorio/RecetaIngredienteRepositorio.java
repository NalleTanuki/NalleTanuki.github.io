package com.ipartek.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.modelo.RecetaIngrediente;

@Repository
public interface RecetaIngredienteRepositorio extends JpaRepository<RecetaIngrediente, Integer> {

}
