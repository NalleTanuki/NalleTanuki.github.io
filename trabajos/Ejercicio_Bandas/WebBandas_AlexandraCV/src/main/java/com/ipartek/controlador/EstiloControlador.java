package com.ipartek.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ipartek.modelo.Banda;
import com.ipartek.modelo.Estilo;
import com.ipartek.servicios.BandaServicio;
import com.ipartek.servicios.EstiloServicio;
import com.ipartek.servicios.PaisServicio;

@Controller
public class EstiloControlador {
	@Autowired
	private EstiloServicio estiloServ;
	@Autowired
	private PaisServicio paisServ;
	@Autowired
	private BandaServicio bandaServ;
	
	@PostMapping("/AgregarEstiloCV")
	public String agregarEstiloCV(@ModelAttribute Banda obj_banda, Model model) {
		
		model.addAttribute("obj_banda", obj_banda);
		
		return "frm_agregar_estilo";
	}
	
	
	@PostMapping("/AgregarEstiloCVReal")
	public String agregarEstiloCVReal(@ModelAttribute Banda obj_banda, Model model, @RequestParam String nuevoEstilo) {
		System.out.println("________________");
		System.out.println(obj_banda);
		System.out.println(nuevoEstilo);
		
		Estilo estiloTemp = estiloServ.insertarEstilo(new Estilo(0, nuevoEstilo));
		obj_banda.setEstilo(estiloTemp);
			
		model.addAttribute("obj_banda", obj_banda);
		
		model.addAttribute("listaPaises", paisServ.obtenerTodosLosPaises());
		model.addAttribute("listaBandas", bandaServ.obtenerTodasLasBandas());
		model.addAttribute("listaEstilos", estiloServ.obtenerTodosLosEstilos());
		
		return "home";
	}
	
}
