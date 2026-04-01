package com.ipartek.servicios;


import java.util.List;

import com.ipartek.modelo.Usuario;

public interface UsuarioServicio {

	List<Usuario> obtenerTodosUsuarios();
	Usuario obtenerUsuarioPorId(Integer id);
	Usuario obtenerUsuarioPorNombre(String nombre);
	Usuario guardarUsuario(Usuario usu);
	Usuario modificarUsuario(Usuario usu);
	Boolean borrarUsuario(Integer id);
	List<Usuario> obtenerTodosUsuariosSaneados();

}
