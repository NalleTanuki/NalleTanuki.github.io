package com.ipartek.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ipartek.auxiliar.Auxiliar;
import com.ipartek.componentes.JwtUtil;
import com.ipartek.modelo.Usuario;
import com.ipartek.pojo.AuthResponse;
import com.ipartek.pojo.MsgError;
import com.ipartek.servicios.UsuarioServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth/")
public class AuthControladorREST {

	@Autowired
	private UsuarioServicio usuarioServ;

	@Autowired
	private JwtUtil jwtUtil;

	
	@Operation(summary = "Autenticación de usuario.")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Autenticación correcta. Devuelve el token JWT.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = String.class)
					)
			),
			
			@ApiResponse(
					responseCode = "401",
					description = "Credenciales incorrectas.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = MsgError.class)
					)
			),
			
			@ApiResponse(
					responseCode = "500",
					description = "Error interno del servidor.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = MsgError.class)
					)
			)
	})
	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody Usuario usu) {
	    try {
	        Usuario usuTemp = usuarioServ.obtenerUsuarioPorNombre(usu.getUser());

	        // Usuario bloqueado/baneado
	        if(usuTemp != null) {
	        	String rol = usuTemp.getRole().getNombre();
	        	
	        	if("BLOQUEADO".equals(rol) || "BANEADO".equals(rol)) {
	        		return ResponseEntity.status(403).body(new MsgError(403, "Usuario bloqueado o baneado."));
	        	}
	        }
	        
	        if (usuTemp != null && usuTemp.getUser() != null) {

	            String hash1 = Auxiliar.hashear(usu.getPass() + "Kolitza");
	            String hashFinal = Auxiliar.hashear(hash1 + usuTemp.getSalt());

	            if (hashFinal.equals(usuTemp.getPass())) {

	                String token = jwtUtil.generateToken(
	                        usuTemp.getUser(),
	                        usuTemp.getRole().getNombre()
	                );
	                
//	                System.out.println("PASS BD: " + usuTemp.getPass());
//	                System.out.println("PASS GENERADA: " + hashFinal);

	                return ResponseEntity.ok(
	                		new AuthResponse(token, usuTemp.getRole().getNombre()));
	            }
	        }

	        return ResponseEntity.status(401).body(new MsgError(401, "Usuario o contraseña incorrectos."));

	    } catch (Exception e) {
	        return ResponseEntity.status(500).body(new MsgError(500, "Error del servidor"));
	    }
	}

}
