package com.ipartek.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ipartek.pojos.Tipo;
import com.ipartek.servicios.rest.TipoServicio;

import jakarta.servlet.http.HttpSession;

@Controller
public class TipoControlador {

	@Autowired
	private TipoServicio tipoServ;

	

	@PostMapping("/InsertarTipo")
	public String insertarTipo(@ModelAttribute Tipo tipo, HttpSession session,
			RedirectAttributes redirectAttributes
	) {

		String token = (String) session.getAttribute("TOKEN");

		if (token == null) {
			return "redirect:/login";
		}

		try {
			tipoServ.insertarTipo(token, tipo);
			return "redirect:/MenuTipos";
			
		} catch (IllegalArgumentException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/MenuTipos";
			
		}catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error inesperado.");
			return "redirect:/MenuTipos";
		}
		
		
	}

	@GetMapping("/EliminarTipo")
	public String eliminarTipo(@RequestParam Integer id, HttpSession session) {

		String token = (String) session.getAttribute("TOKEN");
		
		if(token==null) {
			return "redirect:/login";
		}

		Boolean resultado = tipoServ.borrarTipo(token, id);

		if (resultado != null && resultado == true) {
			return "redirect:/MenuTipos";
		}
		return "redirect:/MenuTipos";
	}

	@GetMapping("/ModificarTipo")
	public String modificarTipo(@RequestParam Integer id, Model model, HttpSession session) {

		String token = (String) session.getAttribute("TOKEN");
		
		if(token==null) {
			return "redirect:/login";
		}


		Tipo tipoTemp = tipoServ.obtenerTipoPorId(token, id);

		if (tipoTemp != null && tipoTemp.getId() > 0) {
			model.addAttribute("obj_tipo", tipoTemp);
			model.addAttribute("tipos", tipoServ.obtenerTodosTipo(token));

			return "frm_modif_tipo";
		}
		return "redirect:/MenuTipos";
	}

	@PostMapping("ModificarTipo")
	public String modificarTipo(@ModelAttribute Tipo obj_tipo, HttpSession session) {

		String token = (String) session.getAttribute("TOKEN");
		
		if(token ==null) {
			return "redirect:/login";
		}

		Tipo tipoTemp = tipoServ.obtenerTipoPorId(token, obj_tipo.getId());
		
		if(tipoTemp != null) {
			tipoTemp.setNombre(obj_tipo.getNombre());
			
			System.out.println("TIPO A ENVIAR: " + tipoTemp.getId() + " - " + tipoTemp.getNombre());
			
			tipoServ.modificarTipo(token, tipoTemp);
			
			return "redirect:/MenuTipos";
		}
		return "redirect:/MenuTipos";
	}

}
