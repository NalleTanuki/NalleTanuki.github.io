package com.ipartek.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.modelo.Ingrediente;

@Repository
public interface IngredienteRepositorio extends JpaRepository<Ingrediente, Integer> {

}
