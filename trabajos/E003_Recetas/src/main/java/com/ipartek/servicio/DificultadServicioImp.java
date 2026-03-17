package com.ipartek.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.modelo.Dificultad;
import com.ipartek.repositorio.DificultadRepositorio;

@Service
public class DificultadServicioImp implements DificultadServicio {
	@Autowired
    private DificultadRepositorio dificultadRepo;

    @Override
    public List<Dificultad> obtenerTodasLasDificultades() {
        try {
            return dificultadRepo.findAllByOrderByIdAsc();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Dificultad obtenerDificultadPorID(Integer id) {
        try {
            return dificultadRepo.findById(id).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
