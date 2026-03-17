package com.ipartek.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.modelo.Banda;

@Repository
public interface BandaRepositorio extends JpaRepository<Banda, Integer> {

}
