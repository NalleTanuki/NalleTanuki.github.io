package com.ipartek.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ipartek.modelo.Ingrediente;
import com.ipartek.modelo.Receta;
import com.ipartek.modelo.RecetaIngrediente;
import com.ipartek.servicio.DificultadServicio;
import com.ipartek.servicio.IngredienteServicio;
import com.ipartek.servicio.PaisServicio;
import com.ipartek.servicio.RecetaServicio;

@Controller
public class RecetaControlador {
	@Autowired
	private RecetaServicio recetaServ;
	@Autowired
	private IngredienteServicio ingredienteServ;
	@Autowired
	private PaisServicio paisServ;
	@Autowired
	private DificultadServicio dificultadServ;

	
	
	@PostMapping("/AgregarReceta")
	public String agregarReceta(@ModelAttribute Receta obj_receta, Model model) {
		
		Receta recetaTemp = recetaServ.insertarReceta(obj_receta);

		if (recetaTemp != null && recetaTemp.getId() > 0) {
			Receta recetaCompleta = recetaServ.obtenerRecetaPorID(recetaTemp.getId());
			
			model.addAttribute("obj_receta", recetaCompleta);
			model.addAttribute("listaIngredientes", ingredienteServ.obtenerTodosLosIngredientes());
			
			return "frm_insertar_ingr_receta"; // si NO falla ve a ingredientes
		}
//		model.addAttribute("mensaje", "No se pudo insertar.");
		return "redirect:/MenuRecetas"; // si falla
	}
	
	

	
	@GetMapping("/BorrarReceta")
	public String borrarReceta(@RequestParam Integer id) {
		Boolean resultado = recetaServ.borrarReceta(id);

		if (resultado != null && resultado == true) {
//			model.addAttribute("mensaje", "Se borró correctamente.");
			return "redirect:/MenuRecetas";
		}
//		model.addAttribute("mensaje", "No se pudo borrar.");
		return "redirect:/MenuRecetas";
	}
	
	
	

	@GetMapping("/ModificarReceta")
	public String modificarReceta(@RequestParam Integer id, Model model) {
		Receta recetaTemp = recetaServ.obtenerRecetaPorID(id);

		if (recetaTemp != null && recetaTemp.getId() > 0) {
			model.addAttribute("obj_receta", recetaTemp);
			model.addAttribute("listaPaises", paisServ.obtenerTodosLosPaises());
			model.addAttribute("listaDificultades", dificultadServ.obtenerTodasLasDificultades());

			return "frm_modif_receta";
		}
		return "redirect:/MenuRecetas";
	}

	@PostMapping("/ModificarReceta")
	public String modificarReceta(@ModelAttribute Receta obj_receta) {
		Receta recetaBD = recetaServ.obtenerRecetaPorID(obj_receta.getId());

		if (recetaBD != null) {
			// Solo actualizar campos basicos
	        recetaBD.setNombre(obj_receta.getNombre());
	        recetaBD.setTiempo(obj_receta.getTiempo());
	        recetaBD.setDificultad(obj_receta.getDificultad());
	        recetaBD.setPais(obj_receta.getPais());

	        recetaServ.modificarReceta(recetaBD);
	        
	        return "redirect:/ModificarIngredientes?id=" + recetaBD.getId();
	        }

		return "redirect:/MenuRecetas";
	}

	
	@GetMapping("/ModificarIngredientes")
	public String modificarIngredientes(@RequestParam Integer id, Model model) {
		Receta receta = recetaServ.obtenerRecetaPorID(id);
		
		List<Ingrediente> listaIngredientes = ingredienteServ.obtenerTodosLosIngredientes();
		
		//Sacar los ids d ls ingredientes qe ya tiene la receta
		List<Integer> ingredientesRecetaIds = receta.getListaIngredientes().stream().map(ri -> ri.getIngrediente().getId()).toList();
		
		// Map para rellenar cantidades
		Map<Integer, String> cantidadesMap = receta.getListaIngredientes().stream().collect(Collectors.toMap(
	                    ri -> ri.getIngrediente().getId(),
	                    ri -> ri.getCantidad()
	            ));
		
		model.addAttribute("obj_receta", receta);
		model.addAttribute("listaIngredientes", ingredienteServ.obtenerTodosLosIngredientes());
		model.addAttribute("ingredientesRecetaIds", ingredientesRecetaIds);
		model.addAttribute("cantidadesMap", cantidadesMap);
		
		
		return "frm_modif_ingr_receta";
	}
	
	
	
	@PostMapping("/AgregarIngredientesReceta") // Recoger los cheks y txt anhadidos
	public String agregarIngredientesReceta(@RequestParam Map<String, String> todosLosParametros) {

		
		int id_receta = Integer.parseInt(todosLosParametros.get("id"));
	    Receta receta = recetaServ.obtenerRecetaPorID(id_receta);

	    List<RecetaIngrediente> listaFinal = new ArrayList<>();

	    for (String key : todosLosParametros.keySet()) {

	        if (key.startsWith("chk_")) {

	            int idIngrediente = Integer.parseInt(todosLosParametros.get(key));

	            String cantidad = todosLosParametros.get("ingr_" + idIngrediente);

	            Ingrediente ingr = ingredienteServ.obtenerIngredientePorID(idIngrediente);

	            RecetaIngrediente ri = new RecetaIngrediente(0, ingr, cantidad, receta);

	            listaFinal.add(ri);
	        }
	    }

	    receta.getListaIngredientes().clear();
	    receta.getListaIngredientes().addAll(listaFinal);

	    recetaServ.modificarReceta(receta);

	    return "redirect:/MenuRecetas";
	}
	
	
	
	
	@GetMapping("/DetallesReceta")
	public String mostrarDetalles(@RequestParam Integer id, Model model) {
		Receta receta = recetaServ.obtenerRecetaPorID(id);
		
		if(receta != null && receta.getId() >0) {
			model.addAttribute("receta", receta);
			return "mostrar_receta";
		}
		
		return "redirect:/MenuRecetas";
	}
	
	
	@GetMapping("/buscar")
	public String buscar(@RequestParam String texto, Model model) {

	    model.addAttribute("listaRecetas", recetaServ.buscarPorIngrediente(texto));

	    return "lista";
	}
}