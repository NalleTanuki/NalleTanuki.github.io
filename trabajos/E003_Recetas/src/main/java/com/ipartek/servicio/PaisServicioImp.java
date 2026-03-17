package com.ipartek.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.modelo.Pais;
import com.ipartek.repositorio.PaisRepositorio;


@Service
public class PaisServicioImp implements PaisServicio {
	@Autowired
	private PaisRepositorio paisRepo;

	@Override
	public List<Pais> obtenerTodosLosPaises() {
		try {
			return paisRepo.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Pais obtenerPaisPorID(Integer id) {
		try {
			return paisRepo.findById(id).orElse(new Pais());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Pais insertarPais(Pais pais) {
		try {
			if(pais.getId()==0) {
				return paisRepo.save(pais);
			} else {
				return new Pais();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Pais modificarPais(Pais pais) {
		try {
			if(pais.getId()>0) {
				return paisRepo.save(pais);
			}else {
				return new Pais();
			}
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean borrarPais(Integer id) {
		try {
			paisRepo.deleteById(id);
			
			Pais pais = obtenerPaisPorID(id);
			
			if(pais==null && pais.getId()==id) {
				return false;
			}else {
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
