package com.ipartek.controlador;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ipartek.pojos.Vehiculo;
import com.ipartek.servicios.rest.MarcaServicio;
import com.ipartek.servicios.rest.TipoServicio;
import com.ipartek.servicios.rest.VehiculoServicio;

import jakarta.servlet.http.HttpSession;

@Controller
public class VehiculoControlador {
	
	@Autowired
	private VehiculoServicio vehiculoServ;
	
	@Autowired
	private MarcaServicio marcaServ;
	
	@Autowired
	private TipoServicio tipoServ;
	
	
	
	
	@PostMapping("/InsertarVehiculo")
	public String agregarVehiculo(
			@ModelAttribute Vehiculo veh,
			HttpSession session,
			RedirectAttributes redirectAttributes
	) {
		
		String token = (String) session.getAttribute("TOKEN");
		
		if(token==null) {
			return "redirect:/login";
		}
		
		try {
			vehiculoServ.insertarVehiculo(token, veh);
			
			return "redirect:/MenuVehiculos";
			
		} catch (IllegalArgumentException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/MenuVehiculos";
			
		} catch(Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error inesperado.");
			return "redirect:/MenuVehiculos";
		}
	}
	
	
	@GetMapping("/EliminarVehiculo")
	public String eliminarVehiculo(@RequestParam Integer id, HttpSession session) {
		
		String token = (String) session.getAttribute("TOKEN");
		
		if(token==null) {
			return "redirect:/login";
		}
		
		Boolean resultado= vehiculoServ.borrarVehiculo(token, id);
		
		if(resultado != null && resultado == true) {
			return "redirect:/MenuVehiculos";
		}
		return "redirect:/MenuVehiculos";
	}
	
	
	@GetMapping("/ModificarVehiculo")
	public String modificarVehiculo(@RequestParam Integer id, Model model, HttpSession session) {
		
		String token = (String) session.getAttribute("TOKEN");
		
		if(token==null) {
			return "redirect:/login";
		}
		
		Vehiculo vehiculoTemp = vehiculoServ.obtenerVehiculoPorId(token, id);
		
		if(vehiculoTemp!=null && vehiculoTemp.getId()>0) {
			model.addAttribute("obj_vehiculo", vehiculoTemp);
			model.addAttribute("marcas", marcaServ.obtenerTodasMarca(token));
			model.addAttribute("tipos", tipoServ.obtenerTodosTipo(token));
			
			return "frm_modif_vehiculo";
		}
		
		return "redirect:/MenuVehiculos";
	}
	
	
	@PostMapping("/ModificarVehiculo")
	public String modificarVehiculo(@ModelAttribute Vehiculo obj_vehiculo, HttpSession session) {
		
		String token = (String) session.getAttribute("TOKEN");
		
		if(token ==null) {
			return "redirect:/login";
		}
		
		Vehiculo vehiculoTemp = vehiculoServ.obtenerVehiculoPorId(token, obj_vehiculo.getId());
		
		if(vehiculoTemp != null) {
			vehiculoTemp.setMarca(obj_vehiculo.getMarca());
			vehiculoTemp.setMatricula(obj_vehiculo.getMatricula());
			vehiculoTemp.setModelo(obj_vehiculo.getModelo());
			vehiculoTemp.setTipo(obj_vehiculo.getTipo());
			
			vehiculoServ.modificarVehiculo(token, vehiculoTemp);
			
			return "redirect:/MenuVehiculos";
		}
		return "redirect:/MenuVehiculos";
	}
	
	
	
	@GetMapping("/buscarVehiculo")
	public String buscarVehiculo(@RequestParam String matricula,
			HttpSession session,
			Model model
	) {
		
		String token = (String) session.getAttribute("TOKEN");
		
		if(token ==null) {
			return "redirect:/login";
		}
		
		try {
			matricula = matricula.toUpperCase();
			
			Vehiculo veh = vehiculoServ.obtenerVehiculoPorMatricula(token, matricula);
			
			if(veh != null && veh.getId() > 0) {
				model.addAttribute("listaVehiculos", List.of(veh));
			}else {
				model.addAttribute("error", "Vehículo no encontrado.");
			}
			
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		
		model.addAttribute("obj_vehiculo", new Vehiculo());
		model.addAttribute("marcas", marcaServ.obtenerTodasMarca(token));
		model.addAttribute("tipos", tipoServ.obtenerTodosTipo(token));
		
		
		return "home";
	}
}
