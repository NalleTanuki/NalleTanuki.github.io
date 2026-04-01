package com.ipartek.controlador;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ipartek.modelo.Tipo;
import com.ipartek.pojo.MsgError;
import com.ipartek.servicios.TipoServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/v1/tipos/")
public class TipoControladorREST {

	@Autowired
	private TipoServicio tipoServ;
	
	
	
	@Operation(summary = "Obtener un listado de todos los tipos de automóviles.")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Listado de tipos obtenido exitosamente.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Tipo.class))
			),
			
			@ApiResponse(
					responseCode = "400",
					description = "No hay tipos registrados.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Tipo.class))
			),
			
			@ApiResponse(responseCode = "500",
			description = "Error interno del servidor.",
			content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = MsgError.class))
			)
		})
	@GetMapping("")
	public ResponseEntity<?> obtenerTodosTipo() {
		try {
			List<Tipo> lista = tipoServ.obtenerTodosTipo();

				return ResponseEntity.ok(lista);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(new MsgError(500, "Error del servidor."));
		}
	}
	
	
	
	@Operation(summary = "Obtener un tipo de automóvil a través del ID")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Tipo obtenido exitosamente.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Tipo.class))
			),
			
			@ApiResponse(
					responseCode = "404",
					description = "No existe ningún tipo con el ID proporcionado.",
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
	public ResponseEntity<?> obtenerTipoPorId(@PathVariable String id) {
		try {
			int idReal = Integer.parseInt(id);
			
			Tipo tipoTemp = tipoServ.obtenerTipoPorId(idReal);
			
			if(tipoTemp.getId() != 0) {
				return ResponseEntity.ok(tipoTemp);
			} else {
				return ResponseEntity.status(404).body(new MsgError(404, "Tipo no obtenido."));
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return ResponseEntity.status(400).body(new MsgError(400, "Error en los parámetros."));
	
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(new MsgError(500, "Error del servidor."));
		}
	}
	
	
	
	@Operation(summary = "Crear un nuevo tipo de automóvil.")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Tipo creado exitosamente.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Tipo.class))
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
	public ResponseEntity<?> insertarTipo(@RequestBody Tipo tipo) {
		try {
			Tipo tipoTemp = tipoServ.insertarTipo(tipo);
			
			if(tipoTemp.getId() != 0) {
				return ResponseEntity.ok(tipoTemp);
			} else {
				return ResponseEntity.status(400).body(new MsgError(400, "Tipo no insertado"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
	        return ResponseEntity.status(500).body(new MsgError(500, "Error del servidor."));
		}
	}
	
	
	
	@Operation(summary = "Actualizar los datos de un tipo de automóvil.")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Datos del tipo actualizados exitosamente.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Tipo.class))
			),
			
			@ApiResponse(
					responseCode = "400",
					description = "Los datos proporcionados no son válidos.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = MsgError.class))
			),
			
			@ApiResponse(responseCode = "404",
			description = "No existe el tipo de automóvil que se intenta modificar.",
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
	public ResponseEntity<?> modificarTipo(@RequestBody Tipo tipo) {
		try {
			
			if(!(tipo instanceof Tipo)) {
				return ResponseEntity.status(400).body(new MsgError(400, "El parámetro no es un tipo."));
			}
			
			Tipo tipoTemp = tipoServ.modificarTipo(tipo);
			
			if(tipoTemp.getId() != 0) {
				return ResponseEntity.ok(tipoTemp);
			} else {
				return ResponseEntity.status(404).body(new MsgError(404, "Tipo no encontrado."));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(new MsgError(500, "Error del servidor."));
		}
	}
	
	
	
	@Operation(summary = "Eliminar un tipo de automóvil.")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Tipo eliminado exitosamente.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Tipo.class))
			),
			
			@ApiResponse(
					responseCode = "404",
					description = "No existe el tipo que se intena eliminar.",
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
	public ResponseEntity<?> borrarTipo(@PathVariable String id){
		
		try {
			int idReal = Integer.parseInt(id);
			
			Boolean resultado = tipoServ.borrarTipo(idReal);
			
			if(resultado != null && resultado == true) {
				return ResponseEntity.ok(resultado);
			} else {
				return ResponseEntity.status(404).body(new MsgError(404, "Tipo no borrado."));
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
