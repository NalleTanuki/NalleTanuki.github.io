package com.ipartek.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ipartek.modelo.Vehiculo;
import com.ipartek.pojo.MsgError;
import com.ipartek.servicios.VehiculoServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/vehiculos/")
public class VehiculoControladorREST {

	@Autowired
	private VehiculoServicio vehiculoServ;

	@Operation(summary = "Obtener un listado de todos los vehículos.")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Listado de vehículos obtenido exitosamente.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Vehiculo.class))
			),
			
			@ApiResponse(
					responseCode = "400",
					description = "No hay vehículos registrados.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Vehiculo.class))
			),
			
			@ApiResponse(responseCode = "500",
			description = "Error interno del servidor.",
			content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = MsgError.class))
			)
		})
	@GetMapping("")
	public ResponseEntity<?> obtenerTodosVehiculos() {
		try {
			List<Vehiculo> lista = vehiculoServ.obtenerTodosVehiculo();

				return ResponseEntity.ok(lista);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(new MsgError(500, "Error del servidor."));
		}
	}

	
	@Operation(summary = "Obtener un vehículo a través del ID.")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Vehículo obtenido exitosamente.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Vehiculo.class))
			),
			
			@ApiResponse(
					responseCode = "404",
					description = "No existe ningún vehículo con el ID proporcionado.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = MsgError.class))
			),
			
			@ApiResponse(
					responseCode = "400",
					description = "El ID proporcionado no tiene un formato válido.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = MsgError.class))
			),
			
			@ApiResponse(responseCode = "500",
			description = "Error interno del servidor.",
			content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = MsgError.class))
			)
		})
	@GetMapping("{id}")
	public ResponseEntity<?> obtenerVehiculoPorId(@PathVariable String id) {
		try {
			int idReal = Integer.parseInt(id);

			Vehiculo vehiculoTemp = vehiculoServ.obtenerVehiculoPorId(idReal);

			if (vehiculoTemp.getId() != 0) {
				return ResponseEntity.ok(vehiculoTemp);
			} else {
				return ResponseEntity.status(404).body(new MsgError(404, "Vehículo por ID no obtenido."));
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
			return ResponseEntity.status(400).body(new MsgError(400, "Error en los parámetros."));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(new MsgError(500, "Error del servidor."));
		}
	}

	
	
	@Operation(summary = "Obtener un vehículo por su matrícula.")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Vehículo obtenido exitosamente.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Vehiculo.class))
			),
			
			@ApiResponse(
					responseCode = "404",
					description = "No existe ningún vehículo con la matrícula proporcionada.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = MsgError.class))
			),
			
			@ApiResponse(
					responseCode = "400",
					description = "La matrícula proporcionada no tiene un formato válido.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = MsgError.class))
			),
			
			@ApiResponse(responseCode = "500",
			description = "Error interno del servidor.",
			content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = MsgError.class))
			)
		})
	@GetMapping("/matricula/{matricula}")
	public ResponseEntity<?> obtenerVehiculoPorMatricula(@PathVariable String matricula) {
		try {
			Vehiculo vehiculoTemp= vehiculoServ.obtenerVehiculoPorMatricula(matricula);
			
			if(vehiculoTemp.getMatricula() != null && !vehiculoTemp.getMatricula().isEmpty()) {
				return ResponseEntity.ok(vehiculoTemp);
			} else {
				return ResponseEntity.status(404).body(new MsgError(404, "Vehículo por matrícula no obtenido."));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(new MsgError(500, "Error del servidor."));
		}
	}
	
	
	
	@Operation(summary = "Crear un nuevo vehículo.")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Vehículo creado exitosamente.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Vehiculo.class))
			),
			
			@ApiResponse(
					responseCode = "400",
					description = "Los datos proporcionados no son válidos.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = MsgError.class))
			),
			
			@ApiResponse(responseCode = "500",
			description = "Error interno del servidor.",
			content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = MsgError.class))
			)
		})
	@PostMapping("")
	public ResponseEntity<?> insertarVehiculo(@RequestBody Vehiculo veh){
		try {
			Vehiculo vehiculoTemp = vehiculoServ.insertarVehiculo(veh);
				return ResponseEntity.ok(vehiculoTemp);
				
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(new MsgError(400, e.getMessage()));
			
		} catch (Exception e) {
			e.printStackTrace();
	        return ResponseEntity.status(500).body(new MsgError(500, "Error del servidor."));
		}
	}

	
	@Operation(summary = "Actualizar los datos de un vehículo.")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Datos del vehículo actualizados exitosamente.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Vehiculo.class))
			),
			
			@ApiResponse(
					responseCode = "400",
					description = "Los datos proporcionados no son válidos.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = MsgError.class))
			),
			
			@ApiResponse(responseCode = "404",
			description = "No existe el vehículo que se intenta modificar.",
			content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = MsgError.class))
			),
			
			@ApiResponse(responseCode = "500",
			description = "Error interno del servidor.",
			content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = MsgError.class))
			)
		})
	@PutMapping("")
	public ResponseEntity<?> modificarVehiculo(@RequestBody Vehiculo veh) {
		try {
			
			if(!(veh instanceof Vehiculo)) {
				return ResponseEntity.status(400).body(new MsgError(400, "El parámetro no es un vehículo."));
			}
			
			Vehiculo vehiculoTemp = vehiculoServ.modificarVehiculo(veh);
			
			if(vehiculoTemp.getId() != 0) {
				return ResponseEntity.ok(vehiculoTemp);
			}else {
				return ResponseEntity.status(404).body(new MsgError(404, "Vehículo por ID no encontrado."));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(new MsgError(500, "Error del servidor."));
		}
	}
	
	
	
	@Operation(summary = "Eliminar un vehículo.")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Vehículo eliminado exitosamente.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Vehiculo.class))
			),
			
			@ApiResponse(
					responseCode = "404",
					description = "No existe el vehículo que se intena eliminar.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = MsgError.class))
			),
			
			@ApiResponse(
					responseCode = "400",
					description = "El ID proporcionado no es válido.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = MsgError.class))
			),
			
			
			@ApiResponse(responseCode = "500",
			description = "Error interno del servidor.",
			content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = MsgError.class))
			)
		})
	@DeleteMapping("{id}")
	public ResponseEntity<?> borrarVehiculo(@PathVariable String id){
		
		try {
			
			int idReal = Integer.parseInt(id);
			
			Boolean resultado = vehiculoServ.borrarVehiculo(idReal);
			
			if(resultado) {
				return ResponseEntity.ok(resultado);
			} else {
				return ResponseEntity.status(404).body(new MsgError(404, "Vehículo no borrado."));
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return ResponseEntity.status(400).body(new MsgError(400, "Error en los parámetros."));
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(new MsgError(500, "Error del servidor."));
		}
		
	}
}
