package com.ipartek.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ipartek.pojos.AuthResponse;
import com.ipartek.servicios.rest.AuthServicio;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginControlador {

	@Autowired
	private AuthServicio authServ;

	
	//Mostrar formulario
	@GetMapping("/login")
	public String mostrarLogin(@RequestParam(required = false) String error, Model model) {
		
		if(error != null) {
			model.addAttribute("error", "Sesión expirada o acceso no autorizado.");
		}
		
		return "login";
	}

	
	
	//Procesar login
	@PostMapping("/login")
	public String login(@RequestParam String user,
						@RequestParam String pass,
						Model model,
						HttpSession session
	) {

		try {
			
			AuthResponse response = authServ.login(user, pass);

			session.setAttribute("TOKEN", response.getToken());
			session.setAttribute("ROLE", response.getRole());
			
//			PRUEBAAAAAAAAAAAAAAAAAA
//			System.out.println("===============================================");
//			System.out.println("USER: " + user);
//			System.out.println("PASS: " + pass);
//			System.out.println("TOKEN: " + response.getToken());
//			System.out.println("===============================================");
			
			return "redirect:/MenuVehiculos";
			
		} catch (RuntimeException e) {
			model.addAttribute("error", e.getMessage());
			return "login";
		}
	}
}
