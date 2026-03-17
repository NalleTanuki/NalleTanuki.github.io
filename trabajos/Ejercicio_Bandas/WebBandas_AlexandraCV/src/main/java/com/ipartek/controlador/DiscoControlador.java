package com.ipartek.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.ipartek.modelo.Disco;
import com.ipartek.servicios.BandaServicio;
import com.ipartek.servicios.DiscoServicio;


@Controller
public class DiscoControlador {

	@Autowired
	private DiscoServicio discoServ;
	@Autowired
	private BandaServicio bandaServ;
	
	
	@GetMapping("/BorrarDisco")
	public String borrarDisco(@RequestParam Integer id, Model model) {
		Boolean resultado = discoServ.borrarDisco(id);
		
		if(resultado!=null && resultado == true) {
			model.addAttribute("mensaje", "Se borró correctamente.");
			return "redirect:/MenuDiscos";
		}
		model.addAttribute("mensaje", "No se pudo borrar.");
		return "redirect:/MenuDiscos";
	}
	
	
	@PostMapping("/AgregarDisco")
	public String agregarDisco(@ModelAttribute Disco obj_disco, Model model) {
		Disco resultado = discoServ.insertarDisco(obj_disco);
		
		if(resultado!=null && resultado.getId()>0) {
			model.addAttribute("mensaje", "Se insertó correctamente.");
			return "redirect:/MenuDiscos";
		}
		model.addAttribute("mensaje", "No se pudo insertar.");
		return "redirect:/MenuDiscos";
	}
	
	
	@GetMapping("/ModificarDisco")
	public String modificarDisco(@RequestParam Integer id, Model model) {		
		Disco discoTemp = discoServ.obtenerDiscoPorID(id);
		
		
		if(discoTemp!=null && discoTemp.getId()>0) {
			model.addAttribute("obj_disco", discoTemp);
			model.addAttribute("listaBandas", bandaServ.obtenerTodasLasBandas());
			
			return "frm_modif_disco";
		}
		
		return "redirect:/MenuDiscos";

	}
	
	
	@PostMapping("/ModificarDisco")
	public String modificarDisco(@ModelAttribute Disco obj_disco) {
		Disco discoTemp = discoServ.modificarDisco(obj_disco);
		
		if(discoTemp!=null && discoTemp.getId()>0) {
			return "redirect:/MenuDiscos";
		}
		
		return "redirect:/MenuDiscos";
	}
}
