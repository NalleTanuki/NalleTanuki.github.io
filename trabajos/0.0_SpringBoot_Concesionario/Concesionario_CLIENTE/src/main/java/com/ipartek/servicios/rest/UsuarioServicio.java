package com.ipartek.servicios.rest;

import java.util.List;

import com.ipartek.pojos.Usuario;


public interface UsuarioServicio {

	List<Usuario> obtenerTodosUsuarios(String token);
	Usuario obtenerUsuarioPorId(String token, Integer id);
	Usuario obtenerUsuarioPorNombre(String token, String nombre);
	Usuario guardarUsuario(String token, Usuario usu);
	Usuario modificarUsuario(String token, Usuario usu);
	Boolean borrarUsuario(String token, Integer id);
	List<Usuario> obtenerTodosUsuariosSaneados(String token);
	
}
