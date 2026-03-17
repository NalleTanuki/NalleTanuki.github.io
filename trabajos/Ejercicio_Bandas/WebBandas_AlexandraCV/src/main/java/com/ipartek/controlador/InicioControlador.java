package com.ipartek.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// la 1a pag qe se muestra

@Controller
public class InicioControlador {
	
	@GetMapping("/")
	// Creamos una funcion - cualquier controlador qe tenga devuelve un String
	public String cargarInicio() {
		
		
		return "redirect:/MenuBandas";
	}
}
