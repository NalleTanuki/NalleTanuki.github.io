package com.ipartek.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ipartek.modelo.Banda;
import com.ipartek.modelo.Pais;
import com.ipartek.servicios.BandaServicio;
import com.ipartek.servicios.EstiloServicio;
import com.ipartek.servicios.PaisServicio;

@Controller
public class PaisControlador {
	@Autowired
	private PaisServicio paisServ;
	@Autowired
	private EstiloServicio estiloServ;
	@Autowired
	private BandaServicio bandaServ;
	
	
	
	@PostMapping("/AgregarPaisCV")
	public String agregarPaisCV(@ModelAttribute Banda obj_banda, Model model, @RequestParam String origen) {
		
		model.addAttribute("obj_banda", obj_banda);
		model.addAttribute("origen", origen);
		
		return "frm_agregar_pais";
	}
	
	
	@PostMapping("/AgregarPaisCVReal")
	public String agregarPaisCVReal(@ModelAttribute Banda obj_banda,
			Model model,
			@RequestParam String nuevoPais,
			@RequestParam String origen)
	{
		Pais paisTemp = paisServ.insertarPais(new Pais(0, nuevoPais)); // insertamos el pais en BD
		
		obj_banda.setPais(paisTemp); //le paso el pais
		
		
		model.addAttribute("obj_banda", obj_banda);	
		model.addAttribute("listaPaises", paisServ.obtenerTodosLosPaises());
		model.addAttribute("listaEstilos", estiloServ.obtenerTodosLosEstilos());
		
		if (origen.equals("home")) {
			model.addAttribute("listaBandas", bandaServ.obtenerTodasLasBandas());
		}
		

		switch (origen) {
			case "home":return "home";
			case "frm_modif_banda":return "frm_modif_banda";
		default:
			return "redirect:/";
		}
	}
}
