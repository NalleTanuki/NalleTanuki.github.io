package com.ipartek.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ipartek.modelo.Vehiculo;
import com.ipartek.repositorio.VehiculoRepositorio;

@Service
public class VehiculoServicioImp implements VehiculoServicio {

	@Autowired
	private VehiculoRepositorio vehiculoRepo;

	@Override
	public List<Vehiculo> obtenerTodosVehiculo() {
		try {
			return vehiculoRepo.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>(); // si pongo null peta
		}
	}

	@Override
	public Vehiculo insertarVehiculo(Vehiculo veh) {
			if (veh.getId()!=0) {
				throw new IllegalArgumentException("El vehículo ya tiene ID.");
			}
			
			try {
				return vehiculoRepo.save(veh);
				
		}catch(DataIntegrityViolationException e) {
			throw new IllegalArgumentException("La matrícula ya existe.");
		} catch(Exception e) {
			throw new RuntimeException ("Error del servidor.");
		}
	}

	@Override
	public Vehiculo obtenerVehiculoPorId(Integer id) {
		try {
			return vehiculoRepo.findById(id).orElse(new Vehiculo());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Vehiculo obtenerVehiculoPorMatricula(String matricula) {
		try {
			return vehiculoRepo.findByMatricula(matricula).orElse(new Vehiculo());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

// PUT - Modifica todo
//	@Override
//	public Vehiculo modificarVehiculo(Vehiculo veh) {
//		try {
//			if (veh.getId()>0) {
//				return vehiculoRepo.save(veh);
//			}else {
//				return new Vehiculo();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}

//	// PATCH - Modifica solo lo necesario
	@Override
	public Vehiculo modificarVehiculo(Vehiculo veh) {
		try {
			Vehiculo vehBD = vehiculoRepo.findById(veh.getId()).orElse(null);

			if (vehBD == null) {
				return new Vehiculo();
			}

			if (veh.getMatricula() != null && !veh.getMatricula().isEmpty()) {
				vehBD.setMatricula(veh.getMatricula());
			}

			if (veh.getModelo() != null && !veh.getModelo().isEmpty()) {
				vehBD.setModelo(veh.getModelo());
			}

			if (veh.getTipo() != null) {
				vehBD.setTipo(veh.getTipo());
			}

			if (veh.getMarca() != null) {
				vehBD.setMarca(veh.getMarca());
			}

			return vehiculoRepo.save(vehBD);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean borrarVehiculo(Integer id) {
		try {
			vehiculoRepo.deleteById(id);

			Vehiculo veh = obtenerVehiculoPorId(id);

			if (veh == null || veh.getId() == id) {
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
