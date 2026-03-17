package com.ipartek.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.modelo.Concierto;

@Repository
public interface ConciertoRepositorio extends JpaRepository<Concierto, Integer> {
	List<Concierto> findAllByOrderByFechaAsc();
}
