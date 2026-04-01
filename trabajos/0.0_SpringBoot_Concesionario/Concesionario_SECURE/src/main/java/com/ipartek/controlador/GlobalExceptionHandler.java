package com.ipartek.controlador;

import java.util.NoSuchElementException;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	// 400 - Errores de datos
	@ExceptionHandler(IllegalArgumentException.class)
	public String error400(Exception ex, Model model) {

		model.addAttribute("mensaje", ex.getMessage());

		return "error/400";
	}

	// 404 - No encontrado
	@ExceptionHandler(NoSuchElementException.class)
	public String error404(Exception ex, Model model) {
		
		model.addAttribute("mensaje", ex.getMessage());

		return "error/404";
	}
	

	// 401 - Login
	@ExceptionHandler(SecurityException.class)
	public String error401(Exception ex, Model model) {
//		model.addAttribute("mensaje", ex.getMessage());

		return "redirect:/login?error";
	}
	
	
	// 403 - Prohibido
	@ExceptionHandler(RuntimeException.class)
	public String errorRuntime(Exception ex, Model model) {
		
		String mensaje = ex.getMessage();
		
		if(mensaje!= null && mensaje.startsWith("403: ")) {
			model.addAttribute("mensaje", mensaje.replace("403:", ""));
			return "error/403";
		}

		model.addAttribute("mensaje", mensaje);

		return "error/500";
	}
		
		
	
	// 500 - Erroes generales
	@ExceptionHandler(Exception.class)
	public String errorGeneral(Exception ex, Model model) {

		model.addAttribute("mensaje", ex.getMessage());

		return "error/500";
	}
}
