package com.ipartek.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.modelo.Estilo;
import com.ipartek.repositorio.EstiloRepositorio;

@Service
public class EstiloServicioImp implements EstiloServicio {
	@Autowired
	private EstiloRepositorio estiloRepo;

	
	@Override
	public List<Estilo> obtenerTodosLosEstilos() {
		try {
			return estiloRepo.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	@Override
	public Estilo obtenerEstiloPorID(Integer id) {
		try {
			return estiloRepo.findById(id).orElse(new Estilo());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	@Override
	public Estilo insertarEstilo(Estilo estilo) {
		try {
			if(estilo.getId()==0) {
				return estiloRepo.save(estilo);
			} else {
				return new Estilo();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	@Override
	public Estilo modificarEstilo(Estilo estilo) {
		try {
			if(estilo.getId()>0) {
				return estiloRepo.save(estilo);
			} else {
				return new Estilo();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	@Override
	public Boolean borrarEstilo(Integer id) {
		try {
			estiloRepo.deleteById(id);

			Estilo estilo = obtenerEstiloPorID(id);
			if(estilo==null || estilo.getId()==id) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
