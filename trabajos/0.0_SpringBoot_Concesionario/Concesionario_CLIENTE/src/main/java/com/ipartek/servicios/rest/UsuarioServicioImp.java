package com.ipartek.servicios.rest;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipartek.pojos.MsgError;
import com.ipartek.pojos.Usuario;

@Service
public class UsuarioServicioImp implements UsuarioServicio {

	private RestTemplate restTemp = new RestTemplate();
	private final String URL = "http://localhost:9090/api/v1/usuarios/";
	
	@Override
	public List<Usuario> obtenerTodosUsuarios(String token) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		try {
			ResponseEntity<Usuario[]> response = restTemp.exchange(
					URL,
					HttpMethod.GET,
					entity,
					Usuario[].class
			);

			return Arrays.asList(response.getBody());
			
		}catch (HttpClientErrorException | HttpServerErrorException e) {
			throw manejarError(e.getResponseBodyAsString());
			
		}catch (RestClientException e) {
			
			throw new RuntimeException("Error de comunicación con la API.");
		}
		
	}

	@Override
	public Usuario obtenerUsuarioPorId(String token, Integer id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		try {
			ResponseEntity<Usuario> reponse = restTemp.exchange(
					URL+id,
					HttpMethod.GET,
					entity,
					Usuario.class
			);
			return reponse.getBody();
			
		}catch (HttpClientErrorException | HttpServerErrorException e) {
			throw manejarError(e.getResponseBodyAsString());
			
		}catch (RestClientException e) {
			
			throw new RuntimeException("Error de comunicación con la API.");
		}
	}
	
	

	@Override
	public Usuario obtenerUsuarioPorNombre(String token, String nombre) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		try {
			ResponseEntity<Usuario> response = restTemp.exchange(
					URL+ "nombre/" +nombre,
					HttpMethod.GET,
					entity,
					Usuario.class
			);
			return response.getBody();
			
		}catch (HttpClientErrorException | HttpServerErrorException e) {
			throw manejarError(e.getResponseBodyAsString());
			
		}catch (RestClientException e) {
			
			throw new RuntimeException("Error de comunicación con la API.");
		}
		
	}

	@Override
	public Usuario guardarUsuario(String token, Usuario usu) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

		HttpEntity<Usuario> entity = new HttpEntity<>(usu, headers);

		try {
			ResponseEntity<Usuario> response = restTemp.exchange(
					URL,
					HttpMethod.POST,
					entity,
					Usuario.class
			);

			return response.getBody();
			
		}catch (HttpClientErrorException | HttpServerErrorException e) {
			throw manejarError(e.getResponseBodyAsString());
			
		}catch (RestClientException e) {
			
			throw new RuntimeException("Error de comunicación con la API.");
		}
		
	}

	
	
	@Override
	public Usuario modificarUsuario(String token, Usuario usu) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		
		HttpEntity<Usuario> entity = new HttpEntity<>(usu, headers);
		
		try {
			ResponseEntity<Usuario> response = restTemp.exchange(
					URL,
					HttpMethod.PUT,
					entity,
					Usuario.class
			);
			return response.getBody();
			
		}catch (HttpClientErrorException | HttpServerErrorException e) {
			throw manejarError(e.getResponseBodyAsString());
			
		}catch (RestClientException e) {
			
			throw new RuntimeException("Error de comunicación con la API.");
		}
		
		
	}

	
	
	@Override
	public Boolean borrarUsuario(String token, Integer id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		try {
			ResponseEntity<Boolean> response = restTemp.exchange(
					URL+id,
					HttpMethod.DELETE,
					entity,
					Boolean.class
			);
			return response.getBody();
			
		}catch (HttpClientErrorException | HttpServerErrorException e) {
			throw manejarError(e.getResponseBodyAsString());
			
		}catch (RestClientException e) {
			
			throw new RuntimeException("Error de comunicación con la API.");
		}
	}

	@Override
	public List<Usuario> obtenerTodosUsuariosSaneados(String token) {
		HttpHeaders headers =new HttpHeaders();
		headers.setBearerAuth(token);
		
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		try {
			ResponseEntity<Usuario[]> response = restTemp.exchange(
					URL+"saneados",
					HttpMethod.GET,
					entity,
					Usuario[].class
			);
			return Arrays.asList(response.getBody());
		
		}catch (HttpClientErrorException | HttpServerErrorException e) {
			throw manejarError(e.getResponseBodyAsString());
			
		}catch (RestClientException e) {
			
			throw new RuntimeException("Error de comunicación con la API.");
		}
		
	}

	
	
	
	// Manejar errores - mensajes
	private RuntimeException manejarError(String body) {
		try {
			// para que no pete si la API devuelve body vacio
			if (body == null || body.isEmpty()) {
				return new RuntimeException("Error sin respuesta de la API.");
			}

			ObjectMapper mapper = new ObjectMapper();
			MsgError error = mapper.readValue(body, MsgError.class);

			switch (error.getCodigo()) {
			case 400:
				return new IllegalArgumentException(error.getMensaje());

			case 404:
				return new NoSuchElementException(error.getMensaje());

			case 401:
				return new SecurityException(error.getMensaje());
				
			case 403:
				return new SecurityException(error.getMensaje());

			default:
				return new RuntimeException(error.getMensaje());
			}

		} catch (Exception e) {
			return new RuntimeException("Error procesando respuesta de la API.");
		}
	}
}
