package com.ipartek.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ipartek.modelo.Marca;
import com.ipartek.pojo.MsgError;
import com.ipartek.servicios.MarcaServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/v1/marcas/")
public class MarcaControladorREST {

	@Autowired
	private MarcaServicio marcaServ;
	
	
	
	@Operation(summary = "Obtener un listado de todas las marcas.")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Listado de marcas obtenido exitosamente.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Marca.class))
			),
			
			@ApiResponse(
					responseCode = "400",
					description = "No hay marcas registradas.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Marca.class))
			),
			
			@ApiResponse(responseCode = "500",
			description = "Error interno del servidor.",
			content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = MsgError.class))
			)
		})
	@GetMapping("")
	public ResponseEntity<?> obtenerTodasMarca() {

		try {
			List<Marca> lista = marcaServ.obtenerTodasMarca();

				return ResponseEntity.ok(lista);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(new MsgError(500, "Error del servidor."));
		}	
	}
	
	
	
	@Operation(summary = "Obtener una marca a través del ID.")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Marca obtenida exitosamente.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Marca.class))
			),
			
			@ApiResponse(
					responseCode = "404",
					description = "No existe ninguna marca con el ID proporcionado.",
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
	public ResponseEntity<?> obtenerMarcaPorId(@PathVariable String id) {
		try {
			int idReal = Integer.parseInt(id);
			
			Marca marcaTemp = marcaServ.obtenerMarcaPorId(idReal);
			
			if(marcaTemp.getId() != 0) {
				return ResponseEntity.ok(marcaTemp);
			} else {
				return ResponseEntity.status(404).body(new MsgError(404, "Marca no encontrada."));
			}
		
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return ResponseEntity.status(400).body(new MsgError(400, "Error en los parámetros."));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(new MsgError(500, "Error del servidor."));
		}
	}
	
	
	
	@Operation(summary = "Crear una nueva marca.")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Marca creada exitosamente.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Marca.class))
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
	public ResponseEntity<?> insertarMarca(@RequestBody Marca marca){
		try {
			Marca marcaTemp =marcaServ.insertarMarca(marca);
			
			if(marcaTemp.getId() != 0) {
				return ResponseEntity.ok(marcaTemp);
			} else {
				return ResponseEntity.status(400).body(new MsgError(400, "Marca no insertada."));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
	        return ResponseEntity.status(500).body(new MsgError(500, "Error del servidor."));
		}
	}
	
	
	
	@Operation(summary = "Actualizar los datos de una marca.")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Datos de la marca actualizados exitosamente.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Marca.class))
			),
			
			@ApiResponse(
					responseCode = "400",
					description = "Los datos proporcionados no son válidos.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = MsgError.class))
			),
			
			@ApiResponse(responseCode = "404",
			description = "No existe la marca que se intenta modificar.",
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
	public ResponseEntity<?> modificarMarca(@RequestBody Marca marca) {
		try {
			
			if(!(marca instanceof Marca)) {
				return ResponseEntity.status(400).body(new MsgError(400, "El parámetro no es una marca."));
			}
			
			Marca marcaTemp = marcaServ.modificarMarca(marca);
			
			if(marcaTemp.getId() !=0) {
				return ResponseEntity.ok(marcaTemp);
			}else {
				return ResponseEntity.status(404).body(new MsgError(404, "Marca no encontrada."));
			}
	
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(new MsgError(500, "Error del servidor."));
		}
	}
	
	
	
	@Operation(summary = "Eliminar una marca.")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Marca eliminada exitosamente.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Marca.class))
			),
			
			@ApiResponse(
					responseCode = "404",
					description = "No existe la marca que se intena eliminar.",
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
	public ResponseEntity<?> borrarMarca(@PathVariable String id){
		try {
			int idReal = Integer.parseInt(id);
			
			Boolean resultado = marcaServ.borrarMarca(idReal);
			
			if(resultado) {
				return ResponseEntity.ok(resultado);
			}else {
				return ResponseEntity.status(404).body(new MsgError(404, "Marca no encontrada."));
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
