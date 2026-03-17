package com.ipartek.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioControlador {
	@GetMapping("/")
	public String cargarinicio() {
		
		
		return "redirect:/MenuRecetas";
		
		
	}
}
