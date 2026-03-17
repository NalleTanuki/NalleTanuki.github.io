package com.ipartek.servicios;

import java.util.List;

import com.ipartek.modelo.Banda;

public interface BandaServicio {
	List<Banda> obtenerTodasLasBandas();
	Banda obtenerBandaPorID(Integer id);	
	Banda insertarBanda(Banda banda);	
	Banda modificarBanda(Banda banda);
	Boolean borrarBanda(Integer id);
	
	List<Banda> obtenerBandasPorIds(List<Integer> ids);
}
