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
import com.ipartek.pojos.Marca;
import com.ipartek.pojos.MsgError;

@Service
public class MarcaServicioImp implements MarcaServicio {

	private RestTemplate restTemp = new RestTemplate();
	private final String URL = "http://localhost:9090/api/v1/marcas/";

	@Override
	public List<Marca> obtenerTodasMarca(String token) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		try {
			ResponseEntity<Marca[]> response = restTemp.exchange(
					URL,
					HttpMethod.GET,
					entity,
					Marca[].class
			);

			return Arrays.asList(response.getBody());
			
		}catch (HttpClientErrorException | HttpServerErrorException e) {
			throw manejarError(e.getResponseBodyAsString());
			
		}catch (RestClientException e) {
			
			throw new RuntimeException("Error de comunicación con la API.");
		}
		
	}

	@Override
	public Marca insertarMarca(String token, Marca marca) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

		HttpEntity<Marca> entity = new HttpEntity<>(marca, headers);

		try {
			ResponseEntity<Marca> response = restTemp.exchange(
					URL,
					HttpMethod.POST,
					entity,
					Marca.class
			);

			return response.getBody();
			
		}catch (HttpClientErrorException | HttpServerErrorException e) {
			throw manejarError(e.getResponseBodyAsString());
			
		}catch (RestClientException e) {
			
			throw new RuntimeException("Error de comunicación con la API.");
		}
		

	}

	
	@Override
	public Marca obtenerMarcaPorId(String token, Integer id) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		try {
			ResponseEntity<Marca> reponse = restTemp.exchange(
					URL+id,
					HttpMethod.GET,
					entity,
					Marca.class
			);
			return reponse.getBody();
		
		}catch (HttpClientErrorException | HttpServerErrorException e) {
			throw manejarError(e.getResponseBodyAsString());
			
		}catch (RestClientException e) {
			
			throw new RuntimeException("Error de comunicación con la API.");
		}
		
	}
	

	@Override
	public Marca modificarMarca(String token, Marca marca) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		
		HttpEntity<Marca> entity = new HttpEntity<>(marca, headers);
		
		try {
			ResponseEntity<Marca> response = restTemp.exchange(
					URL,
					HttpMethod.PUT,
					entity,
					Marca.class
			);
			return response.getBody();
			
		}catch (HttpClientErrorException | HttpServerErrorException e) {
			throw manejarError(e.getResponseBodyAsString());
			
		}catch (RestClientException e) {
			
			throw new RuntimeException("Error de comunicación con la API.");
		}
	}

	
	@Override
	public Boolean borrarMarca(String token, Integer id) {

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
