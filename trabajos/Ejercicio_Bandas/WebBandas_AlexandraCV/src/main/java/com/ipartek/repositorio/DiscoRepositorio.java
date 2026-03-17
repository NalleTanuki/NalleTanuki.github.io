package com.ipartek.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.modelo.Disco;

@Repository
public interface DiscoRepositorio extends JpaRepository<Disco, Integer> {

	public List<Disco> findAllByOrderByPrecioAsc();
	public List<Disco> findAllByOrderByPrecioDesc();
	
	public List<Disco> findAllByBandaId(Integer id);
}
