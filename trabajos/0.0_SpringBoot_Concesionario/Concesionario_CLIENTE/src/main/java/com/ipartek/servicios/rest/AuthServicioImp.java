package com.ipartek.servicios.rest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipartek.pojos.AuthResponse;
import com.ipartek.pojos.MsgError;
import com.ipartek.pojos.Usuario;

@Service
public class AuthServicioImp implements AuthServicio {

	private RestTemplate restTemp = new RestTemplate();
	private final String URL = "http://localhost:9090/api/auth/login";

	public AuthResponse login(String user, String pass) {

		Usuario usu = new Usuario();
		usu.setUser(user);
		usu.setPass(pass);

		HttpEntity<Usuario> entity = new HttpEntity<>(usu);

		try {
			ResponseEntity<AuthResponse> response = restTemp.exchange(
					URL,
					HttpMethod.POST,
					entity,
					AuthResponse.class
			);
			
			return response.getBody();

		} catch (HttpClientErrorException | HttpServerErrorException e) {

			String responseBody = e.getResponseBodyAsString();

			try {
//				System.out.println("BODY: [" + responseBody + "]");
				
				// si viene respuesta JSON d la API
				if (responseBody != null && !responseBody.isEmpty()) {
					
					ObjectMapper mapper = new ObjectMapper();
					MsgError error = mapper.readValue(responseBody, MsgError.class);
					
					if(error.getCodigo() == 401) {
						throw new IllegalArgumentException(error.getMensaje()); //Login mal
					
					}else if (error.getCodigo() == 403){
						throw new IllegalArgumentException(error.getMensaje());  // Usuario bloqueado
					
					} else {
						throw new RuntimeException(error.getMensaje());    // Otros errores
					}
					
				} else  {
					throw new IllegalArgumentException ("Usuario o contraseña incorrectos.");
				}

			} catch (IllegalArgumentException ex) {
				throw ex;
				
			} catch (Exception ex) {
				//Error procesando JSON
				throw new RuntimeException("Error al procesar la respuesta del servidor.");
		}
	} catch(RestClientException e) {
		//Problema d conexion con la API
		throw new RuntimeException("Error de comunicación con la API.");
		}
	}
}
