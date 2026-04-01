package com.ipartek.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ipartek.pojos.Marca;
import com.ipartek.pojos.Rol;
import com.ipartek.pojos.Tipo;
import com.ipartek.pojos.Usuario;
import com.ipartek.pojos.Vehiculo;
import com.ipartek.servicios.rest.MarcaServicio;
import com.ipartek.servicios.rest.RolServicio;
import com.ipartek.servicios.rest.TipoServicio;
import com.ipartek.servicios.rest.UsuarioServicio;
import com.ipartek.servicios.rest.VehiculoServicio;

import jakarta.servlet.http.HttpSession;

@Controller
public class MenuControlador {
	
	@Autowired
	private VehiculoServicio vehiculoServ;
	
	@Autowired
	private TipoServicio tipoServ;
	
	@Autowired
	private MarcaServicio marcaServ;
	
	@Autowired
	private UsuarioServicio usuarioServ;

	@Autowired
	private RolServicio rolServ;
	
	
	
	@GetMapping("/")
	public String mostrarMenu(HttpSession session) {
		
		String token = (String) session.getAttribute("TOKEN");
		
		if(token == null) {
			return "redirect:/login";
		}
		
		return "redirect:/MenuVehiculos";
	}
	
	
	
	@GetMapping("/MenuVehiculos")
	public String mostrarVehiculos(Model model, HttpSession session) {
		
		String token = (String) session.getAttribute("TOKEN");
		String rol = (String) session.getAttribute("ROLE");
		
		if(token ==null) {
			return "redirect:/login";
		}
		
		List<Vehiculo> listaVehiculos = vehiculoServ.obtenerTodosVehiculo(token);
		List<Tipo> listaTipos = tipoServ.obtenerTodosTipo(token);
		List<Marca> listaMarcas= marcaServ.obtenerTodasMarca(token);
		
		model.addAttribute("listaVehiculos", listaVehiculos);
		model.addAttribute("tipos", listaTipos);
		model.addAttribute("marcas", listaMarcas);
		
		model.addAttribute("obj_vehiculo", new Vehiculo());
		
		// para ocultar los botones si NO es admin
		model.addAttribute("esAdmin", "ADMIN".equals(rol));
		
		return "home";
	}
	
	
	
	@GetMapping("/MenuMarcas")
	public String mostrarMarcas(Model model, HttpSession session) {
		
		String token = (String) session.getAttribute("TOKEN");
		String rol = (String) session.getAttribute("ROLE");
		
		if(token == null) {
			return "redirect:/login";
		}
		
		List<Marca> listaMarcas = marcaServ.obtenerTodasMarca(token);
		
		model.addAttribute("listaMarcas", listaMarcas);
		model.addAttribute("obj_marca", new Marca());
		
		model.addAttribute("esAdmin", "ADMIN".equals(rol));
		
		return "marcas";
	}
	
	
	
	@GetMapping("/MenuTipos")
	public String mostrarTipos(Model model, HttpSession session) {
		
//		System.out.println("ENTRA EN MENU TIPOS");
		
		String token = (String) session.getAttribute("TOKEN");
		String rol = (String) session.getAttribute("ROLE");
		
		if(token== null) {
			return "redirect:/login";
		}
		
		List<Tipo> listaTipos = tipoServ.obtenerTodosTipo(token);
		
		model.addAttribute("listaTipos", listaTipos);
		model.addAttribute("obj_tipo", new Tipo());
		
		model.addAttribute("esAdmin", "ADMIN".equals(rol));
		
		return "tipos";
	}
	
	
	
	@GetMapping("/MenuUsuarios")
	public String mostrarUsuarios(Model model, HttpSession session) {
		
		String token = (String) session.getAttribute("TOKEN");
		String rol = (String) session.getAttribute("ROLE");
		if(token== null) {
			return "redirect:/login";
		}
		
		if(!"ADMIN".equals(rol)) {
			return "redirect:/login";
		}
		
		List<Usuario> listaUsuarios =usuarioServ.obtenerTodosUsuarios(token);
		List<Rol> listaRoles = rolServ.obtenerTodosLosRoles(token);
		
		model.addAttribute("listaUsuarios", listaUsuarios);
		model.addAttribute("listaRoles", listaRoles);
		model.addAttribute("obj_usuario", new Usuario());
		
		return "usuarios";
	}
	
	
	
	@GetMapping("/MenuBackup")
	public String menuBackup(Model model, HttpSession session) {
		String token = (String) session.getAttribute("TOKEN");
		String rol = (String) session.getAttribute("ROLE");
		
		if(token == null) {
			return "redirect:/login";
		}
		
		if (!"ADMIN".equals(rol)) {

			return "redirect:/MenuVehiculos";
		}
		
		model.addAttribute("esAdmin", "ADMIN".equals(rol));
		
		return "backup";
	}
	
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		
		return "redirect:/login";
	}
}
