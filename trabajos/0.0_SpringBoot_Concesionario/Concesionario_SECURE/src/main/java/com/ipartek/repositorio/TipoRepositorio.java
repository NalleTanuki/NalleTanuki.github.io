package com.ipartek.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.modelo.Tipo;

@Repository
public interface TipoRepositorio extends JpaRepository<Tipo, Integer> {

}
