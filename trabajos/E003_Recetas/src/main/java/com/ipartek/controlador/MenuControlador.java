package com.ipartek.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ipartek.modelo.Ingrediente;
import com.ipartek.modelo.Receta;
import com.ipartek.servicio.DificultadServicio;
import com.ipartek.servicio.IngredienteServicio;
import com.ipartek.servicio.PaisServicio;
import com.ipartek.servicio.RecetaServicio;

@Controller
public class MenuControlador {
	@Autowired
	private PaisServicio paisServ;
	@Autowired
	private DificultadServicio dificultadServ;
	@Autowired
	private RecetaServicio recetaServ;
	@Autowired
	private IngredienteServicio ingredienteServ;
	
	
	
	@GetMapping("/MenuRecetas")
	public String menuRecetas(Model model) {
		model.addAttribute("obj_receta", new Receta());
		
		model.addAttribute("listaPaises", paisServ.obtenerTodosLosPaises());
		model.addAttribute("listaDificultades", dificultadServ.obtenerTodasLasDificultades());
		model.addAttribute("listaRecetas", recetaServ.obtenerTodasLasRecetas());
		
		return "home";
	}
	
	
	@GetMapping("/MenuIngredientes")
	public String menuIngredientes(Receta receta, Model model) {
		Receta recetaGuardada =recetaServ.insertarReceta(receta);
		
		Receta recetaCompleta= recetaServ.obtenerRecetaPorID(recetaGuardada.getId());
		
		model.addAttribute("recetaInsertada", recetaCompleta);
		model.addAttribute("obj_receta", new Receta());
		
		return "ingredientes";
	}
	
	
	@GetMapping("/lista")
	public String mostrarLista(Model model) {
		model.addAttribute("listaRecetas", recetaServ.obtenerTodasLasRecetas());
		
		return "lista";
	}
	
	
	@GetMapping("/ingredientes")
	public String listaIngredientes(Model model) {
		model.addAttribute("listaIngredientes", ingredienteServ.obtenerTodosLosIngredientes());
		model.addAttribute("obj_ingrediente", new Ingrediente());
		
		return "ingredientes";
	}
}
