package com.ipartek.controlador;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ipartek.pojos.Marca;
import com.ipartek.pojos.Tipo;
import com.ipartek.pojos.Vehiculo;
import com.ipartek.servicios.rest.MarcaServicio;
import com.ipartek.servicios.rest.TipoServicio;
import com.ipartek.servicios.rest.VehiculoServicio;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class BackupControlador {

	
	@Autowired
	private VehiculoServicio vehiculoServ;
	
	@Autowired
	private MarcaServicio marcaServ;
	
	@Autowired
	private TipoServicio tipoServ;
	
	
	@GetMapping("/BackupCSV")
	public void descargarCSV(HttpSession session, HttpServletResponse response) throws Exception {
		
		String token = (String) session.getAttribute("TOKEN");
		String rol = (String) session.getAttribute("ROLE");
		
		if(token == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		if(!"ADMIN".equals(rol)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		
		
		//Obtener datos
		List<Vehiculo> vehiculos = vehiculoServ.obtenerTodosVehiculo(token);
		List<Marca> marcas = marcaServ.obtenerTodasMarca(token);
		List<Tipo> tipos = tipoServ.obtenerTodosTipo(token);
		
		
		// Nombre archivo
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyyMMdd_HHmm");
		String timestamp = LocalDateTime.now().format(formato);
		String nombreArchivo = "backup_concesionario_" + timestamp + ".zip";
		
		response.setContentType("application/zip");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\"");
		
		try(ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream())){
			
			// VEHICULOS
			zipOut.putNextEntry(new ZipEntry("vehiculos.csv"));
			
			StringBuilder vehiculosCSV = new StringBuilder();
			vehiculosCSV.append("id,marca,modelo,matricula,tipo\n");
			
			 for (Vehiculo v : vehiculos) {
				 vehiculosCSV.append(v.getId()).append(",")
	                			.append(v.getMarca().getNombre()).append(",")
	                			.append(v.getModelo()).append(",")
	                			.append(v.getMatricula()).append(",")
	                			.append(v.getTipo().getNombre()).append("\n");
	          }
			 
			 zipOut.write(vehiculosCSV.toString().getBytes());
			 zipOut.closeEntry();
			 
			 
			 
			 // MARCAS
			 zipOut.putNextEntry(new ZipEntry("marcas.csv"));
			 
			 StringBuilder marcasCSV = new StringBuilder();
			 marcasCSV.append("id,nombre\n");

			 for (Marca m : marcas) {
				 marcasCSV.append(m.getId()).append(",")
	               		   .append(m.getNombre()).append("\n");
	            }
			 
			 zipOut.write(marcasCSV.toString().getBytes());
			 zipOut.closeEntry();
			 
			 
			 // TIPOS
			 zipOut.putNextEntry(new ZipEntry("tipos.csv"));

			 StringBuilder tiposCSV = new StringBuilder();
			 tiposCSV.append("id,nombre\n");


			 for (Tipo t : tipos) {
				 tiposCSV.append(t.getId()).append(",")
				 		  .append(t.getNombre()).append("\n");
				 }


			 zipOut.write(tiposCSV.toString().getBytes());

			 zipOut.closeEntry();
			}
		}
}
