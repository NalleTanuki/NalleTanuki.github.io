package com.ipartek.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ipartek.pojos.Marca;
import com.ipartek.servicios.rest.MarcaServicio;

import jakarta.servlet.http.HttpSession;

@Controller
public class MarcaControlador {
	
	
	@Autowired
	private MarcaServicio marcaServ;
	
	
	@PostMapping("/InsertarMarca")
	public String insertarMarca(
			@ModelAttribute Marca marca,
			HttpSession session,
			RedirectAttributes redirectAttributes
	) {
		
		String token = (String) session.getAttribute("TOKEN");
		
		if(token == null) {
			return "redirect:/login";
		}
		
		try {
			marcaServ.insertarMarca(token, marca);
			
			return "redirect:/MenuMarcas";
		} catch (IllegalArgumentException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/MenuMarcas";
			
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error inesperado.");
			return "redirect:/MenuMarcas";
		}

	}
	
	
	@GetMapping("/EliminarMarca")
	public String eliminarMarca(@RequestParam Integer id, HttpSession session) {
		
		String token = (String) session.getAttribute("TOKEN");
		
		if(token==null) {
			return "redirect:/login";
		}
		
		Boolean resultado = marcaServ.borrarMarca(token, id);
		
		if(resultado != null && resultado == true) {
			return "redirect:/MenuMarcas";
		}
		
		return "redirect:/MenuMarcas";
	}
	
	
	@GetMapping("/ModificarMarca")
	public String modificarMarca(@RequestParam Integer id, Model model, HttpSession session) {

		String token = (String) session.getAttribute("TOKEN");
		
		if(token ==null) {
			return "redirect:/login";
		}
		
		Marca marcaTemp = marcaServ.obtenerMarcaPorId(token, id);
		
		if(marcaTemp != null && marcaTemp.getId() > 0) {
			model.addAttribute("obj_marca", marcaTemp);
			model.addAttribute("marcas", marcaServ.obtenerTodasMarca(token));
			
			return "frm_modif_marca";
		}
		return "redirect:/MenuMarcas";
	}
	
	
	
	@PostMapping("/ModificarMarca")
	public String modificarMarca(@ModelAttribute Marca obj_marca, HttpSession session) {
		
		String token = (String) session.getAttribute("TOKEN");
		
		if(token ==null) {
			return "redirect:/login";
		}
		
		Marca marcaTemp =marcaServ.obtenerMarcaPorId(token, obj_marca.getId());
		
		if(marcaTemp != null) {
			marcaTemp.setNombre(obj_marca.getNombre());
			
			marcaServ.modificarMarca(token, marcaTemp);
			
			return "redirect:/MenuMarcas";
		}
		return "redirect:/MenuMarcas";
	}
}
