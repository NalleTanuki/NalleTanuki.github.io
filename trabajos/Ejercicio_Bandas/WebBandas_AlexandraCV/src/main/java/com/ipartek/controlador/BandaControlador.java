package com.ipartek.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ipartek.modelo.Banda;
import com.ipartek.modelo.Disco;
import com.ipartek.servicios.BandaServicio;
import com.ipartek.servicios.DiscoServicio;
import com.ipartek.servicios.EstiloServicio;
import com.ipartek.servicios.PaisServicio;

@Controller
public class BandaControlador {
	@Autowired
	private BandaServicio bandaServ;
	
	@Autowired
	private EstiloServicio estiloServ;
	
	@Autowired
	private PaisServicio paisServ;
	
	@Autowired
	private DiscoServicio discoServ;
	
	
	
	@GetMapping("/BorrarBanda")
	//public String borrarBanda(@RequestParam(name="id") Integer id_banda) VALEN LAS 2 FORMAS
	public String borrarBanda(@RequestParam Integer id, Model model) {
		
		Boolean resultado = bandaServ.borrarBanda(id); // dsp de borrar 1  me devuelve resul (true/false)
		
		if(resultado!=null && resultado==true) { //entonces se elimino la banda
			model.addAttribute("mensaje", "Se borró correctamente."); //q dentro del modelo crear una tributo dnde mostrar un mensaje
			return "redirect:/MenuBandas"; // redirigir al controlador qe carga tda la BD
		}
		model.addAttribute("mensaje", "No se pudo borrar.");
		return "redirect:/MenuBandas";
	}
	
	
	
	// con POST xq es un formulario
	@PostMapping("/AgregarBanda")
	public String agregarBanda(@ModelAttribute Banda obj_banda, Model model) {
	
		Banda resultado = bandaServ.insertarBanda(obj_banda);
		
		if(resultado!=null && resultado.getId()>0) { //entonces inserto correctamente
			model.addAttribute("mensaje", "Se insertó correctamente.");
			return "redirect:/MenuBandas";
		}
		
		model.addAttribute("mensaje", "No se pudo insertar.");
		return "redirect:/MenuBandas";
	}
	
	
	
	
	//Recibe el id > Busca la banda > la envia al form = NO modifica nada
	@GetMapping("/ModificarBanda")
	public String modificarBanda(@RequestParam Integer id, Model model) {		
		Banda bandaTemp = bandaServ.obtenerBandaPorID(id);
		
		if(bandaTemp!=null && bandaTemp.getId()>0) {
			model.addAttribute("obj_banda", bandaTemp);
			model.addAttribute("listaPaises", paisServ.obtenerTodosLosPaises());
			model.addAttribute("listaEstilos", estiloServ.obtenerTodosLosEstilos());
			
			
			return "frm_modif_banda";
		}
	
		return "redirect:/MenuBandas";
	}
	
	
	@PostMapping("/ModificarBanda") //Aqui solo modificamos, no comprobamos si esta bien o mal
	public String modificarBanda(@ModelAttribute Banda obj_banda) {
		Banda bandaTemp = bandaServ.modificarBanda(obj_banda);
		
		if(bandaTemp!=null && bandaTemp.getId()>0) {
			return "redirect:/MenuBandas";
		}
		
		return "redirect:/MenuBandas";
	}
	
	
	
	@PostMapping("/AgregarBandaCV")
	public String agregarBandaCV(@ModelAttribute Disco obj_disco, Model model, @RequestParam String origen) {
		
		model.addAttribute("obj_disco", obj_disco);
		model.addAttribute("obj_banda", new Banda());
		model.addAttribute("listaPaises", paisServ.obtenerTodosLosPaises());
		model.addAttribute("listaEstilos", estiloServ.obtenerTodosLosEstilos());
		model.addAttribute("origen", origen);
		
		return "frm_agregar_banda";
	}

	
	@PostMapping("/AgregarBandaCVReal")
	public String agregarBandaCVReal(@ModelAttribute Banda obj_banda,
									 @ModelAttribute Disco obj_disco,
	                                 @RequestParam String origen,
	                                 Model model) {

	    Banda bandaTemp = bandaServ.insertarBanda(obj_banda);
	    
	    obj_disco.setBanda(bandaTemp);
	    
	    model.addAttribute("obj_disco", obj_disco);
 	    model.addAttribute("listaBandas", bandaServ.obtenerTodasLasBandas());
	    model.addAttribute("listaDiscos", discoServ.obtenerTodosLosDiscos());
	    
	    return "discos";
	}
}
