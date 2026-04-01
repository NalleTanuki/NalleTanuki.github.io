package com.ipartek.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ipartek.pojos.Rol;
import com.ipartek.pojos.Usuario;
import com.ipartek.servicios.rest.RolServicio;
import com.ipartek.servicios.rest.UsuarioServicio;

import jakarta.servlet.http.HttpSession;


@Controller
public class UsuarioControlador {

	@Autowired
	private UsuarioServicio usuarioServ;
	@Autowired
	private RolServicio rolServ;
	

	@PostMapping("/InsertarUsuario")
	public String insertarUsuario(@ModelAttribute Usuario usu, HttpSession session) {

		String token = (String) session.getAttribute("TOKEN");
		if (token == null) {
			return "redirect:/login";
		}

		usuarioServ.guardarUsuario(token, usu);

		return "redirect:/MenuUsuarios";
	}

	
	@GetMapping("/EliminarUsuario")
	public String eliminarUsuario(@RequestParam Integer id, HttpSession session) {

		String token = (String) session.getAttribute("TOKEN");
		
		if(token==null) {
			return "redirect:/login";
		}

		Boolean resultado = usuarioServ.borrarUsuario(token, id);

		if (resultado != null && resultado == true) {
			return "redirect:/MenuUsuarios";
		}
		return "redirect:/MenuUsuarios";
	}

	
	@GetMapping("/ModificarUsuario")
	public String modificarUsuario(@RequestParam Integer id, Model model, HttpSession session) {

		String token = (String) session.getAttribute("TOKEN");
		
		if(token ==null) {
			return "redirect:/login";
		}
		
		Usuario usuarioTemp = usuarioServ.obtenerUsuarioPorId(token, id);
		
		List<Rol> listaRoles = rolServ.obtenerTodosLosRoles(token);
		
		if(usuarioTemp != null && usuarioTemp.getId() > 0 ) {
			model.addAttribute("obj_usuario", usuarioTemp);
			model.addAttribute("roles", listaRoles);
			
			return "frm_modif_usuario";
		}
		
		return "redirect:/MenuUsuarios";
	}

	
	@PostMapping("/ModificarUsuario")
	public String modificarUsuario(@ModelAttribute Usuario obj_usuario, HttpSession session) {
		String token = (String) session.getAttribute("TOKEN");
		
		if(token ==null) {
			return "redirect:/login";
		}
		
		
		Usuario usuarioTemp= usuarioServ.obtenerUsuarioPorId(token, obj_usuario.getId());
		
		if(usuarioTemp != null) {
			usuarioTemp.setUser(obj_usuario.getUser());
			usuarioTemp.setRole(obj_usuario.getRole());
			usuarioTemp.setPass(obj_usuario.getPass());
			
			usuarioServ.modificarUsuario(token, usuarioTemp);
			
			return "redirect:/MenuUsuarios";
		}
		return "redirect:/MenuUsuarios";
	}
	
}
