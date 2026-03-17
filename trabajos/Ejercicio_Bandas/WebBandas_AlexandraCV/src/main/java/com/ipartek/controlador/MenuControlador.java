package com.ipartek.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ipartek.modelo.Banda;
import com.ipartek.modelo.Concierto;
import com.ipartek.modelo.Disco;
import com.ipartek.servicios.BandaServicio;
import com.ipartek.servicios.ConciertoServicio;
import com.ipartek.servicios.DiscoServicio;
import com.ipartek.servicios.EstiloServicio;
import com.ipartek.servicios.PaisServicio;


@Controller
public class MenuControlador {
	
	@Autowired // todos los autowired van a interfaces
	// con esto cada vez que ponga paisServ. me saldran tdas las sentencias creadas en Paises
	private PaisServicio paisServ;
	@Autowired
	private BandaServicio bandaServ;
	@Autowired
	private EstiloServicio estiloServ;
	@Autowired
	private DiscoServicio discoServ;
	@Autowired
	private ConciertoServicio conciertoServ;
	
	
	// Creamos con el metodo GET (todos los enlaces son GET)
	// Model model es el modelo d datos d envio d spring, lo necesitamos poner para
	// guardar y enviar
	@GetMapping("/MenuBandas")
	public String menuBandas(Model model) { // recibo los parametros(paso 1) y los maqueto (paso 2) en model
		
		//List<Pais> listaPaises = paisServ.obtenerTodosLosPaises();  // Paso 4
		//model.addAttribute(listaPaises); // Paso 6 - Envio
		
		// Lo de arriba quedaria asi (un servlet):
		model.addAttribute("listaPaises", paisServ.obtenerTodosLosPaises());
		model.addAttribute("listaBandas", bandaServ.obtenerTodasLasBandas());
		model.addAttribute("listaEstilos", estiloServ.obtenerTodosLosEstilos());
			
		model.addAttribute("obj_banda", new Banda());

		
		return "home"; // la redireccion, el paso 7
	}
	
	
	
	
	@GetMapping("/MenuDiscos")
	public String menuDiscos(@RequestParam(required=false) String orden, Model model) {
		List<Disco> lista;
		if("precioAsc".equals(orden)) {
			lista= discoServ.obtenerTodosLosDiscosOrdenadosPrecioASC();
		} else if("precioDesc".equals(orden)) {
			lista = discoServ.obtenerTodosLosDiscosOrdenadosPrecioDESC();
		} else {
			lista = discoServ.obtenerTodosLosDiscos();
		}
		
		model.addAttribute("listaDiscos", lista);
		model.addAttribute("listaBandas", bandaServ.obtenerTodasLasBandas());
		
		model.addAttribute("obj_disco", new Disco());
		
		return "discos";
	}
	
	
	
	
	
	@GetMapping("/MenuConciertos")
	public String menuConciertos(@RequestParam(required=false) String orden, Model model) {
		
		List<Concierto> lista;
		if("fechaAsc".equals(orden)) {
			lista = conciertoServ.obtenerTodosLosConciertosOrdenadosFechaASC();
		} else {
			lista =conciertoServ.obtenerTodosLosConciertos();
		}
		
		model.addAttribute("listaConciertos", lista);
		model.addAttribute("listaPaises", paisServ.obtenerTodosLosPaises());
		model.addAttribute("listaBandas", bandaServ.obtenerTodasLasBandas());
		
		model.addAttribute("obj_concierto", new Concierto());
		
		return "conciertos";
	}
}