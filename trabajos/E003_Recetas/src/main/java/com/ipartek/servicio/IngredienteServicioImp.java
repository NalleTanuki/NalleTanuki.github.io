package com.ipartek.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.modelo.Ingrediente;
import com.ipartek.repositorio.IngredienteRepositorio;

@Service
public class IngredienteServicioImp implements IngredienteServicio {
	@Autowired
	private IngredienteRepositorio ingredienteRepo;

	@Override
	public List<Ingrediente> obtenerTodosLosIngredientes() {
		try {
			return ingredienteRepo.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Ingrediente obtenerIngredientePorID(Integer id) {
		try {
			return ingredienteRepo.findById(id).orElse(new Ingrediente());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Ingrediente insertarIngrediente(Ingrediente ingrediente) {
		try {
			if(ingrediente.getId()==0) {
				return ingredienteRepo.save(ingrediente);
			}else {
				return new Ingrediente();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Ingrediente modificarIngrediente(Ingrediente ingrediente) {
		try {
			if(ingrediente.getId()>0) {
				return ingredienteRepo.save(ingrediente);
			}else {
				return new Ingrediente();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean borrarIngrediente(Integer id) {
		try {
			ingredienteRepo.deleteById(id);
			
			Ingrediente ingrediente =obtenerIngredientePorID(id);
			
			if(ingrediente==null && ingrediente.getId()==id) {
				return false;
			}else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
