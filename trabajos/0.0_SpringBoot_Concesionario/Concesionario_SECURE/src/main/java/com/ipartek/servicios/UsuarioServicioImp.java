package com.ipartek.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.modelo.Usuario;
import com.ipartek.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServicioImp implements UsuarioServicio {
	
	@Autowired
	private UsuarioRepositorio usuarioRepo;

	@Override
	public List<Usuario> obtenerTodosUsuarios() {
		try {
			return usuarioRepo.findAll();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Usuario obtenerUsuarioPorId(Integer id) {
		try {
			return usuarioRepo.findById(id).orElse(new Usuario());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Usuario obtenerUsuarioPorNombre(String nombre) {
		try {
			return usuarioRepo.findByUser(nombre);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Usuario guardarUsuario(Usuario usu) {
		try {
			if(usu.getId()==0) {
				return usuarioRepo.save(usu);
			} else {
				return new Usuario();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	@Override
	public Usuario modificarUsuario(Usuario usu) {
		try {
			if(usu.getId() > 0) {
				return usuarioRepo.save(usu);
			} else {
				return new Usuario();
			}
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean borrarUsuario(Integer id) {
		try {
			usuarioRepo.deleteById(id);
			
			Usuario usu = obtenerUsuarioPorId(id);
			
			if(usu==null || usu.getId()==id) {
				return false;
			} else {
				return true;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Usuario> obtenerTodosUsuariosSaneados() {
		try {
			return usuarioRepo.findAllSaniticed();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
