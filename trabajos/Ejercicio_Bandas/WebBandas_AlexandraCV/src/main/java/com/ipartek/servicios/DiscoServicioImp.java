package com.ipartek.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.modelo.Disco;
import com.ipartek.repositorio.DiscoRepositorio;

@Service
public class DiscoServicioImp implements DiscoServicio {
	@Autowired
	private DiscoRepositorio discoRepo;
	
	@Override
	public List<Disco> obtenerTodosLosDiscos(){
		try {
			return discoRepo.findAll();
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Disco obtenerDiscoPorID(Integer id) {
		try {
			return discoRepo.findById(id).orElse(new Disco());
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Disco insertarDisco(Disco disco) {
		try {
			if(disco.getId()==0) {
				return discoRepo.save(disco);
			} else {
				return new Disco();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Disco modificarDisco(Disco disco) {
		try {
			if(disco.getId()>0) {
				return discoRepo.save(disco);
			} else {
				return new Disco();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean borrarDisco(Integer id) {
		try {
			discoRepo.deleteById(id);
			
			Disco disco = obtenerDiscoPorID(id);
			
			if(disco==null || disco.getId()==id) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	@Override
	public List<Disco> obtenerTodosLosDiscosOrdenadosPrecioASC() {
		try {
			return discoRepo.findAllByOrderByPrecioAsc();
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Disco> obtenerTodosLosDiscosOrdenadosPrecioDESC() {
		try {
			return discoRepo.findAllByOrderByPrecioDesc();
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	

	@Override
	public List<Disco> obtenerTodosLosDiscosPorBanda(Integer id) {
		try {
			return discoRepo.findAllByBandaId(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
