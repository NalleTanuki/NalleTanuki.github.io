package com.ipartek.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ipartek.modelo.Banda;
import com.ipartek.modelo.Concierto;
import com.ipartek.servicios.BandaServicio;
import com.ipartek.servicios.ConciertoServicio;
import com.ipartek.servicios.PaisServicio;

@Controller
public class ConciertoControlador {
	@Autowired
	private ConciertoServicio conciertoServ;
	@Autowired
	private BandaServicio bandaServ;
	
	@Autowired
	private PaisServicio paisServ;
	

	@PostMapping("/AgregarConcierto")
	public String agregarConcierto (@ModelAttribute Concierto obj_concierto, @RequestParam List<Integer> idsBandas) {
		List<Banda> bandasSeleccionadas = bandaServ.obtenerBandasPorIds(idsBandas);
		
		obj_concierto.setListaBandas(bandasSeleccionadas);
		
		conciertoServ.insertarConcierto(obj_concierto);
		
		return "redirect:/MenuConciertos";
	}
	
	
	@GetMapping("/BorrarConcierto")
	public String borrarConcierto(@RequestParam Integer id, Model model) {
		Boolean resultado = conciertoServ.borrarConcierto(id);
		
		if(resultado!=null && resultado==true) {
			model.addAttribute("mensaje", "Se borró correctamente.");
			return "redirect:/MenuConciertos";
		}
		model.addAttribute("mensaje", "No se pudo borrar.");
		return "redirect:/MenuConciertos";
	}
	
	
	@GetMapping("/ModificarConcierto")
	public String modificarConcierto(@RequestParam Integer id, Model model) {
		Concierto conciTemp = conciertoServ.obtenerConciertoPorID(id);
		
		if(conciTemp!=null && conciTemp.getId()>0) {
			model.addAttribute("obj_concierto", conciTemp);
			model.addAttribute("listaPaises", paisServ.obtenerTodosLosPaises());
			model.addAttribute("listaBandas", bandaServ.obtenerTodasLasBandas());
			
			return "frm_modif_concierto";
		}
		return "redirect:/MenuConciertos";
	}
	
	@PostMapping("/ModificarConcierto")
	public String modificarConcierto(@ModelAttribute Concierto obj_concierto) {
		
//		List<Banda> bandasSelecionadas = bandaServ.obtenerBandasPorIds(idsBandas);
//		obj_concierto.setListaBandas(bandasSelecionadas);
		
		Concierto conciTemp = conciertoServ.modificarConcierto(obj_concierto);
		
		if(conciTemp!=null && conciTemp.getId()>0) {
			return "redirect:/MenuConciertos";
		}
		return "redirect:/MenuConciertos";
	}
}
