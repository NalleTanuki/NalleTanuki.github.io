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
import com.ipartek.pojos.Vehiculo;

@Service
public class VehiculoServicioImp implements VehiculoServicio {
	
	private RestTemplate restTemp = new RestTemplate();
	private final String URL = "http://localhost:9090/api/v1/vehiculos/";

	
	@Override
	public List<Vehiculo> obtenerTodosVehiculo(String token) {
		
//		Header
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		
//		Entrada
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		try {
//			Respuesta
			ResponseEntity<Vehiculo[]> response = restTemp.exchange(
					URL,
					HttpMethod.GET,
					entity,
					Vehiculo[].class
			);
			
			return Arrays.asList(response.getBody());
			
		}catch (HttpClientErrorException | HttpServerErrorException e) {
			throw manejarError(e.getResponseBodyAsString());
			
		}catch (RestClientException e) {
			
			throw new RuntimeException("Error de comunicación con la API.");
		}		
	}

	
	@Override
	public Vehiculo insertarVehiculo(String token, Vehiculo veh) {
		
//		Header
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		
//		Entrada
		HttpEntity<Vehiculo> entity = new HttpEntity<>(veh, headers);
		
		try {
//			Respuesta
			ResponseEntity<Vehiculo> response = restTemp.exchange(
					URL,
					HttpMethod.POST,
					entity,
					Vehiculo.class
			);
			
			return response.getBody();
			
		}catch (HttpClientErrorException | HttpServerErrorException e) {
			throw manejarError(e.getResponseBodyAsString());
			
		}catch (RestClientException e) {
			
			throw new RuntimeException("Error de comunicación con la API.");
		}
	}
	
	

	@Override
	public Vehiculo obtenerVehiculoPorId(String token, Integer id) {
		
//		Header
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		
//		Entrada
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		try {
//			Respuesta
			ResponseEntity<Vehiculo> reponse = restTemp.exchange(
					URL+id,
					HttpMethod.GET,
					entity,
					Vehiculo.class
			);
			return reponse.getBody();
			
		}catch (HttpClientErrorException | HttpServerErrorException e) {
			throw manejarError(e.getResponseBodyAsString());
			
		}catch (RestClientException e) {
			
			throw new RuntimeException("Error de comunicación con la API.");
		}

	}

	@Override
	public Vehiculo obtenerVehiculoPorMatricula(String token, String matricula) {
		
//		Header
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		
//		Entrada
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		
		try {
//			Respuesta
			ResponseEntity<Vehiculo> response = restTemp.exchange(
					URL+ "matricula/" +matricula,
					HttpMethod.GET,
					entity,
					Vehiculo.class
			);
			return response.getBody();
			
		}catch (HttpClientErrorException | HttpServerErrorException e) {
			throw manejarError(e.getResponseBodyAsString());
			
		}catch (RestClientException e) {
			
			throw new RuntimeException("Error de comunicación con la API.");
		}

	}

	
	
	@Override
	public Vehiculo modificarVehiculo(String token, Vehiculo veh) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		
		HttpEntity<Vehiculo> entity = new HttpEntity<>(veh, headers);
		
		
		try {
			ResponseEntity<Vehiculo> response = restTemp.exchange(
					URL,
					HttpMethod.PUT,
					entity,
					Vehiculo.class
			);
			
			return response.getBody();
		}catch (HttpClientErrorException | HttpServerErrorException e) {
			throw manejarError(e.getResponseBodyAsString());
			
		}catch (RestClientException e) {
			
			throw new RuntimeException("Error de comunicación con la API.");
		}
	}

	@Override
	public Boolean borrarVehiculo(String token, Integer id) {
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
	
	
	
	
	
	//Manejar errores - mensajes
	private RuntimeException manejarError(String body) {
		
//		System.out.println("BODY ERROR RAW: " + body);
		
		try {
			//para que no pete si la API devuelve body vacio
			if(body == null || body.isEmpty()) {
				return new RuntimeException("Error sin respuesta de la API.");
			}
			
			ObjectMapper mapper = new ObjectMapper();
			MsgError error= mapper.readValue(body, MsgError.class);
			
			switch(error.getCodigo()) {
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
			
			
		}catch (Exception e) {
			return new RuntimeException("Error procesando respuesta de la API.");
		}
	}
}
