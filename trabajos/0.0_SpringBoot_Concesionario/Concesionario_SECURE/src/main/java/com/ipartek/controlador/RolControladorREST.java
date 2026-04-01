package com.ipartek.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ipartek.modelo.Rol;
import com.ipartek.pojo.MsgError;
import com.ipartek.servicios.RolServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/api/v1/roles/")
public class RolControladorREST {

	@Autowired
	private RolServicio rolServ;
	
	
	
	@Operation(summary = "Obtener un listado de todos los roles.")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Listado de roles obtenido exitosamente.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Rol.class)
					)
			),
			
			@ApiResponse(
					responseCode = "400",
					description = "No hay roles registrados.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Rol.class))
			),
			
			@ApiResponse(responseCode = "500",
			description = "Error interno del servidor.",
			content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = MsgError.class))
			)
	})
	@GetMapping("")
	public ResponseEntity<?> obtenerTodosRoles() {
		try {
			List<Rol> lista = rolServ.obtenerTodosLosRoles();
				return ResponseEntity.ok(lista);
					
					
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(new MsgError(500, "Error del servidor."));
		}
	}
	
	
	
	
	@Operation(summary = "Obtener un rol a través del ID")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Rol obtenido exitosamente.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Rol.class))
			),
			
			@ApiResponse(
					responseCode = "404",
					description = "No existe ningún rol con el ID proporcionado.",
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
	public ResponseEntity<?> obtenerRolPorId(@PathVariable String id){
		try {
			int idReal = Integer.parseInt(id);
			
			Rol rolTemp = rolServ.obtenerRolPorID(idReal);
			
			if(rolTemp.getId() != 0) {
				return ResponseEntity.ok(rolTemp);
			} else {
				return ResponseEntity.status(404).body(new MsgError(404, "No existe el rol con el ID proporcionado."));
			}
		
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return ResponseEntity.status(400).body(new MsgError(400, "Error en los parámetros."));

		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(new MsgError(500, "Error del servidor."));
		}
	}
}
