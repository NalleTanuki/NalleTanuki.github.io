package com.ipartek.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ipartek.modelo.Receta;

@Repository
public interface RecetaRepositorio extends JpaRepository<Receta, Integer> {
	@Query(value = "SELECT DISTINCT r.* " +
		       "FROM recetas r " +
		       "JOIN receta_ingrediente ri ON r.id = ri.fk_receta " +
		       "JOIN ingredientes i ON ri.id_ingrediente = i.id " +
		       "WHERE MATCH(i.nombre) AGAINST (CONCAT(:texto,'*') IN BOOLEAN MODE)",
		       nativeQuery = true)
		List<Receta> buscarPorIngrediente(@Param("texto") String texto);
}