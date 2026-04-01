package com.ipartek.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ipartek.modelo.Marca;
import com.ipartek.repositorio.MarcaRepositorio;
import com.ipartek.repositorio.VehiculoRepositorio;

import jakarta.transaction.Transactional;

@Service
public class MarcaServicioImp implements MarcaServicio {
	
	@Autowired
	private MarcaRepositorio marcaRepo;
	
	@Autowired
	private VehiculoRepositorio vehiculoRepo;
	

	@Override
	public List<Marca> obtenerTodasMarca() {
		try {
			return marcaRepo.findAll();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Marca insertarMarca(Marca marca) {
		try {
			return marcaRepo.save(marca);
			
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("La marca ya existe.");
		
		} catch (Exception e) {
			throw new RuntimeException("Error del servidor.");
		}
	}

	@Override
	public Marca obtenerMarcaPorId(Integer id) {
		try {
			return marcaRepo.findById(id).orElse(new Marca());
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Marca modificarMarca(Marca marca) {
		try {
			if(marca.getId() > 0) {
				return marcaRepo.save(marca);
			}else {
				return new Marca();
			}
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	@Override
	@Transactional
	public Boolean borrarMarca(Integer id) {
		try {
			
			// 1- Borrar vehiculos asociados
			vehiculoRepo.deleteByMarca_Id(id);
			
			// 2 - Borrar marca
			marcaRepo.deleteById(id);
			
//			Marca marca = obtenerMarcaPorId(id);
//			
//			if(marca==null || marca.getId() == id) {
//				return false;
//			}else {
//				return true;
//			}
			return true;
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
