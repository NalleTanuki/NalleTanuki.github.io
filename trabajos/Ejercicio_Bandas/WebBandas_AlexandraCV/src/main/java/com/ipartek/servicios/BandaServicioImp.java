package com.ipartek.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.modelo.Banda;
import com.ipartek.repositorio.BandaRepositorio;

@Service
public class BandaServicioImp implements BandaServicio {
	@Autowired
	private BandaRepositorio bandaRepo;

	@Override
	public List<Banda> obtenerTodasLasBandas() {
		try {
			return bandaRepo.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Banda obtenerBandaPorID(Integer id) {
		try {
			return bandaRepo.findById(id).orElse(new Banda());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Banda insertarBanda(Banda banda) {
		try {
			if(banda.getId()==0) {
				return bandaRepo.save(banda);
			} else {
				return new Banda();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Banda modificarBanda(Banda banda) {
		try {
			if(banda.getId()>0) {
				return bandaRepo.save(banda);
			} else {
				return new Banda();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean borrarBanda(Integer id) {
		try {
			bandaRepo.deleteById(id);

			Banda banda = obtenerBandaPorID(id);
			if(banda==null || banda.getId()==id) {
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
	public List<Banda> obtenerBandasPorIds(List<Integer> ids) {
		return bandaRepo.findAllById(ids);
	}

}
