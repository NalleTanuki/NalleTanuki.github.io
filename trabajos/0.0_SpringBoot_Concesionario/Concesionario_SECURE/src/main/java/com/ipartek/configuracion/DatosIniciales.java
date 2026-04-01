package com.ipartek.configuracion;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ipartek.auxiliar.Auxiliar;
import com.ipartek.modelo.Marca;
import com.ipartek.modelo.Rol;
import com.ipartek.modelo.Tipo;
import com.ipartek.modelo.Usuario;
import com.ipartek.repositorio.MarcaRepositorio;
import com.ipartek.repositorio.RolRepositorio;
import com.ipartek.repositorio.TipoRepositorio;
import com.ipartek.repositorio.UsuarioRepositorio;


//No uso @Transactional xq son operaciones independientes.

// Solo seria necesario si varias operaciones dependieran entre si
// y quisiera qe todas se ejecuten o ninguna (rollback)
//x ejemplo, si crease VEHICULOS ahi si seria necesario usarlo

// No creo vehiculos xq no tiene sentido ya qe no me puedo inventar la matricula en un caso real
@Configuration
public class DatosIniciales {

	@Bean CommandLineRunner initDatos(
			MarcaRepositorio marcaRepo,
			TipoRepositorio tipoRepo,
			RolRepositorio rolRepo,
			UsuarioRepositorio usuRepo)
	{
		return args ->{
			System.out.println("Inicializando datos en backend...");
			
			
			// MARCAS
			if(marcaRepo.count() == 0) {
				
				Marca m1 = new Marca();
				m1.setNombre("Toyota");
				
				Marca m2 = new Marca();
				m2.setNombre("Audi");
				
				Marca m3 = new Marca();
				m3.setNombre("Mercedes-Benz");
				
				Marca m4 = new Marca();
				m4.setNombre("Subaru");
				
				
				marcaRepo.save(m1);
				marcaRepo.save(m2);
				marcaRepo.save(m3);
				marcaRepo.save(m4);
				
				
				System.out.println("Marcas creadas.");
			}
			
			
			//TIPOS
			if(tipoRepo.count()== 0) {
				
				Tipo t1 = new Tipo();
				t1.setNombre("Berlina");
				
				Tipo t2 = new Tipo();
				t2.setNombre("SUV");
				
				Tipo t3 = new Tipo();
				t3.setNombre("Moto");
				
				Tipo t4 = new Tipo();
				t4.setNombre("Camión");
				
				tipoRepo.save(t1);
				tipoRepo.save(t2);
				tipoRepo.save(t3);
				tipoRepo.save(t4);
				
				System.out.println("Tipos creados.");
			}
			
			
			// ROLES
			if(rolRepo.count() == 0) {
				
				Rol r1 = new Rol();
				r1.setNombre("ADMIN");
				
				Rol r2 = new Rol();
				r2.setNombre("USUARIO");
				
				Rol r3 = new Rol();
				r3.setNombre("BLOQUEADO");
				
				Rol r4 = new Rol();
				r4.setNombre("BANEADO");
				
				rolRepo.save(r1);
				rolRepo.save(r2);
				rolRepo.save(r3);
				rolRepo.save(r4);
				
				System.out.println("Roles creados.");
			}
			
			
			// USUARIOS - admin
			if(usuRepo.count() == 0) {
				
				String salt = Auxiliar.generarSal(16);
				String passFinal =Auxiliar.hashearPasswords("1234", salt);
				
				Rol rolAdmin = rolRepo.findByNombre("ADMIN");
				
				System.out.println("ROL ADMIN: " + rolAdmin);
				
				Usuario admin = new Usuario();
				
				admin.setUser("admin");
				admin.setSalt(salt);
				admin.setPass(passFinal);
				admin.setRole(rolAdmin);
				
				usuRepo.save(admin);
				
				System.out.println("Usuario ADMIN creado.");
			}
		};
	}
	
}
