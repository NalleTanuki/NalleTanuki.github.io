package com.ipartek.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ipartek.modelo.Ingrediente;
import com.ipartek.servicio.IngredienteServicio;

@Controller
public class IngredienteControlador {
	@Autowired
	private IngredienteServicio ingredienteServ;

	@PostMapping("/AgregarIngrediente")
	public String agregarIngrediente(@ModelAttribute Ingrediente obj_ingrediente, Model model) {

		ingredienteServ.insertarIngrediente(obj_ingrediente);

	    return "redirect:/ingredientes";
	}
	
	

	@GetMapping("/ModificarIngrediente")
	public String modificaIngrediente(@RequestParam Integer id, Model model) {
		Ingrediente ingrediente = ingredienteServ.obtenerIngredientePorID(id);

	    model.addAttribute("obj_ingrediente", ingrediente);

	    return "frm_modif_ingrediente";
	}
	
	
	@PostMapping("/ModificarIngrediente")
	public String modificarIngrediente(@ModelAttribute Ingrediente obj_ingrediente) {

	    ingredienteServ.modificarIngrediente(obj_ingrediente);

	    return "redirect:/ingredientes";
	}
}
