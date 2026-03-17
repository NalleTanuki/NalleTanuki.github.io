package com.ipartek.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.modelo.Concierto;
import com.ipartek.repositorio.ConciertoRepositorio;

@Service
public class ConciertoServicioImp implements ConciertoServicio {
	@Autowired
	private ConciertoRepositorio conciertoRepo;

	@Override
	public List<Concierto> obtenerTodosLosConciertos() {
		try {
			return conciertoRepo.findAll();
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Concierto> obtenerTodosLosConciertosOrdenadosFechaASC() {
		try {
			return conciertoRepo.findAllByOrderByFechaAsc();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Concierto obtenerConciertoPorID(Integer id) {
		try {
			return conciertoRepo.findById(id).orElse(new Concierto());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Concierto insertarConcierto(Concierto concierto) {
		try {
			if(concierto.getId()==0) {
				return conciertoRepo.save(concierto);
			} else {
				return new Concierto();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Concierto modificarConcierto(Concierto concierto) {
		try {
	        if(concierto.getId() > 0) {
	            return conciertoRepo.save(concierto);
	        } else {
	            return null;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	@Override
	public Boolean borrarConcierto(Integer id) {
		try {
			conciertoRepo.deleteById(id);
			
			Concierto conci = obtenerConciertoPorID(id);
			
			if(conci==null || conci.getId()==0) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;		}
	}
	
	
}
