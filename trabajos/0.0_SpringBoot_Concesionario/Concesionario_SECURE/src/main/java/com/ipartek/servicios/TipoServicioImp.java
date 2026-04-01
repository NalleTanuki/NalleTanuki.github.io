package com.ipartek.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.ipartek.modelo.Tipo;
import com.ipartek.repositorio.TipoRepositorio;
import com.ipartek.repositorio.VehiculoRepositorio;

import jakarta.transaction.Transactional;

@Service
public class TipoServicioImp implements TipoServicio {

	
	@Autowired
	private TipoRepositorio tipoRepo;

	@Autowired
	private VehiculoRepositorio vehiculoRepo;
	

	@Override
	public List<Tipo> obtenerTodosTipo() {
		try {
			return tipoRepo.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Tipo insertarTipo(Tipo tipo) {
		try {
			return tipoRepo.save(tipo);
			
		}catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("El tipo de vehículo ya existe.");
			
		}catch (Exception e) {
			throw new RuntimeException("Error del servidor.");
		}
	}

	@Override
	public Tipo obtenerTipoPorId(Integer id) {
		try {
			return tipoRepo.findById(id).orElse(new Tipo());
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Tipo modificarTipo(Tipo tipo) {
		try {
			if(tipo.getId() > 0) {
				return tipoRepo.save(tipo);
			} else {
				return new Tipo();
			}
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Transactional
	public Boolean borrarTipo(Integer id) {
		try {
			
			// 1 - Borrar vehiculos asociados
			vehiculoRepo.deleteByTipo_Id(id);
			
			// 2 - Borrar tipo
			tipoRepo.deleteById(id);
			
//			Tipo tipo = obtenerTipoPorId(id);
			
//			if(tipo == null || tipo.getId() == id) {
//				return false;
//			} else {
//				return true;
//			}
			return true;
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
