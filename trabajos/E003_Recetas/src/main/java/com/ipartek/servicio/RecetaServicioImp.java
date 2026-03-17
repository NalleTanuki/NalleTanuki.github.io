package com.ipartek.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.modelo.Receta;
import com.ipartek.repositorio.RecetaRepositorio;

@Service
public class RecetaServicioImp implements RecetaServicio {
	@Autowired
	private RecetaRepositorio recetaRepo;

	@Override
	public List<Receta> obtenerTodasLasRecetas() {
		try {
			return recetaRepo.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	
	@Override
	public Receta obtenerRecetaPorID(Integer id) {
		try {
			return recetaRepo.findById(id).orElse(new Receta());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	@Override
	public Receta insertarReceta(Receta receta) {
		try {
			if(receta.getId()==0) {
				return recetaRepo.save(receta);
			}else {
				return new Receta();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

	@Override
	public Receta modificarReceta(Receta receta) {
		try {
			if(receta.getId()>0) {
				return recetaRepo.save(receta);
			}else {
				return new Receta();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	

	@Override
	public Boolean borrarReceta(Integer id) {
		try {
			recetaRepo.deleteById(id);
			
			Receta receta=obtenerRecetaPorID(id);
			
			if(receta==null && receta.getId()==id) {
				return false;
			}else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	public List<Receta> buscarPorIngrediente(String texto){
		return recetaRepo.buscarPorIngrediente(texto);
	}
}
