package com.ipartek.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.modelo.Vehiculo;

@Repository
public interface VehiculoRepositorio extends JpaRepository<Vehiculo, Integer> {
	
	Optional<Vehiculo> findByMatricula(String matricula);

	
	// Para eliminar un vehiculo si elimino una marca y un tipo asociado
	void deleteByMarca_Id(int id);
	void deleteByTipo_Id(int id);
}
