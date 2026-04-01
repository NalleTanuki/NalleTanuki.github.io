package com.ipartek.controlador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ipartek.auxiliar.Auxiliar;
import com.ipartek.modelo.Usuario;
import com.ipartek.pojo.MsgError;
import com.ipartek.servicios.UsuarioServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/usuarios/")
public class UsuarioControladorREST {

	@Autowired
	private UsuarioServicio usuarioServ;
	
	
	@Operation(summary = "Obtener un listado de todos los usuarios.")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Listado de usuarios obtenido exitosamente.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Usuario.class))
			),
			
			@ApiResponse(
					responseCode = "400",
					description = "No hay usuarios registrados.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Usuario.class))
			),
			
			@ApiResponse(responseCode = "500",
			description = "Error interno del servidor.",
			content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = MsgError.class))
			)
		})
	@GetMapping("")
	public ResponseEntity<?> obtenerTodosUsuarios(){
		try {
			List<Usuario> lista= usuarioServ.obtenerTodosUsuarios();

				return ResponseEntity.ok(lista);

		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(new MsgError(500, "Error del servidor."));
		}
	}
	
	
	
	
	@Operation(summary = "Obtener un usuario a través del ID")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Usuario obtenido exitosamente.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Usuario.class))
			),
			
			@ApiResponse(
					responseCode = "404",
					description = "No existe ningún usuario con el ID proporcionado.",
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
	public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable String id){
		try {
			int idReal = Integer.parseInt(id);
			
			Usuario usuarioTemp = usuarioServ.obtenerUsuarioPorId(idReal);
			
			if(usuarioTemp.getId() != 0) {
				return ResponseEntity.ok(usuarioTemp);
			} else {
				return ResponseEntity.status(404).body(new MsgError(404, "No existe el usuario con el ID proporcionado."));
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return ResponseEntity.status(400).body(new MsgError(400, "Error en los parámetros."));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(new MsgError(500, "Error del servidor."));
		}
	}
	
	
	
	@Operation(summary = "Obtener un usuario por su nombre.")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Usuario obtenido exitosamente.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Usuario.class))
			),
			
			@ApiResponse(
					responseCode = "404",
					description = "No existe ningún usuario con el nombre proporcionado.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = MsgError.class))
			),
			
			@ApiResponse(
					responseCode = "400",
					description = "El usuario proporcionado no tiene un formato válido.",
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
	@GetMapping("/nombre/{nombre}")
	public ResponseEntity<?> obtenerUsuarioPorNombre(@PathVariable String nombre){
		try {
			Usuario usuarioTemp = usuarioServ.obtenerUsuarioPorNombre(nombre);
			
			if(usuarioTemp.getUser() != null && !usuarioTemp.getUser().isEmpty()) {
				return ResponseEntity.ok(usuarioTemp);
			} else {
				return ResponseEntity.status(404).body(new MsgError(404, "Usuario por nombre no obtenido."));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(new MsgError(500, "Error del servidor."));
		}
	}
	
	
	@Operation(summary = "Crear un nuevo usuario.")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Usuario creado exitosamente.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Usuario.class))
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
	public ResponseEntity<?> guardarUsuario(@RequestBody Usuario usu){
		try {
			
			//Generar salt
			String salt = Auxiliar.generarSal(16);
			
			
			// Hashear contrasenha
			String hash1 = Auxiliar.hashear(usu.getPass() + "Kolitza");
			String hashFinal = Auxiliar.hashear(hash1 + salt);
			
			// Setear
			usu.setSalt(salt);
			usu.setPass(hashFinal);
			
			//Guardar
			Usuario usuarioTemp=usuarioServ.guardarUsuario(usu);
			
			if(usuarioTemp.getId() != 0) {
				return ResponseEntity.ok(usuarioTemp);
			}else {
				return ResponseEntity.status(400).body(new MsgError(400, "Usuario no insertado."));
			}
			
		}catch (Exception e) {
			e.printStackTrace();
	        return ResponseEntity.status(500).body(new MsgError(500, "Error del servidor."));
		}
	}
	
	
	
	@Operation(summary = "Actualizar los datos de un usuario.")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Datos del usuario actualizados exitosamente.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Usuario.class))
			),
			
			@ApiResponse(
					responseCode = "400",
					description = "Los datos proporcionados no son válidos.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = MsgError.class))
			),
			
			@ApiResponse(responseCode = "404",
			description = "No existe el usuario que se intenta modificar.",
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
	public ResponseEntity<?> modificarUsuario(@RequestBody Usuario usu){
		try {
			
			if(!(usu instanceof Usuario)) {
				return ResponseEntity.status(400).body(new MsgError(400, "El parámetro no es un usuario."));
			}
			
			Usuario usuarioTemp= usuarioServ.modificarUsuario(usu);
			
			if(usuarioTemp.getId() !=0) {
				return ResponseEntity.ok(usuarioTemp);
			} else {
				return ResponseEntity.status(404).body(new MsgError(404, "Usuario por ID no encontrado."));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
	        return ResponseEntity.status(500).body(new MsgError(500, "Error del servidor."));
		}
	}
	
	
	
	@Operation(summary = "Eliminar un usuario.")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Usuario eliminado exitosamente.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Usuario.class))
			),
			
			@ApiResponse(
					responseCode = "404",
					description = "No existe el usuario que se intena eliminar.",
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
	public ResponseEntity<?> borrarUsuario(@PathVariable String id){
		try {
			int idReal = Integer.parseInt(id);
			
			Boolean resultado = usuarioServ.borrarUsuario(idReal);
			
			if(resultado) {
				return ResponseEntity.ok(resultado);
			} else {
				return ResponseEntity.status(404).body(new MsgError(404, "Usuario no borrado."));
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return ResponseEntity.status(400).body(new MsgError(400, "Error en los parámetros."));
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(new MsgError(500, "Error del servidor."));
		}
	}
	
	
	
	@Operation(summary = "Obtener un listado saneado de todos los usuarios.")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Listado saneado de usuarios obtenido exitosamente.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Usuario.class))
			),
			
			@ApiResponse(
					responseCode = "400",
					description = "No hay usuarios registrados.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Usuario.class))
			),
			
			@ApiResponse(responseCode = "500",
			description = "Error interno del servidor.",
			content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = MsgError.class))
			)
		})
	@GetMapping("saneados")
	public ResponseEntity<?> obtenerTodosUsuariosSaneados(){
		try {
			
			List<Usuario> lista = usuarioServ.obtenerTodosUsuariosSaneados();
			
			if(lista != null && !lista.isEmpty()) {
				return ResponseEntity.ok(lista);
			} else {
				return ResponseEntity.status(200).body(new ArrayList<>());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(new MsgError(500, "Error del servidor."));
		}
	}
}
