package com.ipartek.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ipartek.modelo.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {

	@Query(value = "SELECT id, user, '' AS pass , '' AS salt, role_id FROM usuarios;", nativeQuery = true)
	List<Usuario> findAllSaniticed();
	
	Usuario findByUser(String usu);
}
