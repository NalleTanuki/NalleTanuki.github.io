package com.ipartek.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.modelo.Pais;
import com.ipartek.repositorio.PaisRepositorio;

@Service
public class PaisServicioImp implements PaisServicio {

	@Autowired // es para qe lo d abajo se autodirija, qe funcione cuando le llamen
	// para qe lo dirija al repositorio y le ponemos un nombre parecido (paisRepo)
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
		// y si no lo encuentra pues qe devuelva un nuevo pais		
		try {
			return paisRepo.findById(id).orElse(new Pais());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	@Override
	public Pais insertarPais(Pais pais) {
		// pra insertar y modificar se usa el mismo metodo: SAVE
		
		try {
			if(pais.getId()==0) {    		  // si del pais qe me llega su ID es 0
				return paisRepo.save(pais);   // pues me lo insertas
			} else {  				 		  //si el id es distinto de 0
				return new Pais();   		  // devuelve un pais en blanco
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	@Override
	public Pais modificarPais(Pais pais) {
		try {
			if(pais.getId()>0) {    // si del pais qe me llega su ID es mayor qe 0
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
	public Boolean borrarPais(Integer id) {
		try {
			paisRepo.deleteById(id);  // Borramos

			// comprobamos si borro de verdad
			Pais pais = obtenerPaisPorID(id);
			if(pais==null || pais.getId()==id) { // si pais es = null O el ID qe me viene es un id
				return false;
			} else {							// si no se cumplen esas condiciones
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
