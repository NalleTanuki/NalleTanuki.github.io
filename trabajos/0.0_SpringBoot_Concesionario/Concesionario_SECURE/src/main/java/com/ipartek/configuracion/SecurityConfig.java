package com.ipartek.configuracion;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ipartek.componentes.JwtRequestFilter;
 
@Configuration
@EnableWebSecurity
public class SecurityConfig {
 
	@Autowired
	private JwtRequestFilter jwtFilter;
	
	@Autowired
	private CustomAuthEntryPoint customAuthEntryPoint;
 
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
//        		.securityMatcher("/api/**")
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                		
                		//Login LIBRE - Deja loguearse a cualquiera
                		.requestMatchers("/api/auth/**").permitAll()
                		
                		//Permitir SWAGGER
						.requestMatchers(
								"/swagger-ui.html", 
								"/swagger-ui/**",
								"/v3/api-docs/**",
								"/swagger-resources/**", 
								"/webjars/**",
								"/api-docs/**", 
								"/swagger-ui/index.html")
						.permitAll()
						
						.requestMatchers(HttpMethod.GET,"/api/v1/vehiculos/**").hasAnyRole("ADMIN","USUARIO")
                		.requestMatchers(HttpMethod.POST, "/api/v1/vehiculos/**").hasAnyRole("ADMIN","USUARIO")
						.requestMatchers(HttpMethod.PUT, "/api/v1/vehiculos/**").hasAnyRole("ADMIN", "USUARIO")
						.requestMatchers(HttpMethod.DELETE, "/api/v1/vehiculos/**").hasAnyRole("ADMIN")
						
                		.requestMatchers(HttpMethod.GET, "/api/v1/tipos/**").hasAnyRole("ADMIN","USUARIO")
                		.requestMatchers(HttpMethod.POST, "/api/v1/tipos/**").hasAnyRole("ADMIN","USUARIO")
                		.requestMatchers(HttpMethod.PUT, "/api/v1/tipos/**").hasAnyRole("ADMIN", "USUARIO")
                		.requestMatchers(HttpMethod.DELETE, "/api/v1/tipos/**").hasAnyRole("ADMIN")
                		
                		.requestMatchers(HttpMethod.GET, "/api/v1/marcas/**").hasAnyRole("ADMIN","USUARIO")
                		.requestMatchers(HttpMethod.POST, "/api/v1/marcas/**").hasAnyRole("ADMIN","USUARIO")
                		.requestMatchers(HttpMethod.PUT, "/api/v1/marcas/**").hasAnyRole("ADMIN", "USUARIO")
                		.requestMatchers(HttpMethod.DELETE, "/api/v1/marcas/**").hasAnyRole("ADMIN")
                		
//                		.requestMatchers("/api/v1/usuarios/**").hasRole("ADMIN") es lo mismo qe lo de abajo simplificado
                		.requestMatchers(HttpMethod.GET, "/api/v1/usuarios/**").hasAnyRole("ADMIN")
                		.requestMatchers(HttpMethod.POST, "/api/v1/usuarios/**").hasAnyRole("ADMIN")
                		.requestMatchers(HttpMethod.PUT, "/api/v1/usuarios/**").hasAnyRole("ADMIN")
                		.requestMatchers(HttpMethod.DELETE, "/api/v1/usuarios/**").hasAnyRole("ADMIN")
                		
//                		.requestMatchers("/api/v1/roles/**").hasRole("ADMIN") es lo mismo qe lo de abajo simplificado
                		.requestMatchers(HttpMethod.GET, "/api/v1/roles/**").hasAnyRole("ADMIN")
                		.requestMatchers(HttpMethod.POST, "/api/v1/roles/**").hasAnyRole("ADMIN")
                		.requestMatchers(HttpMethod.PUT, "/api/v1/roles/**").hasAnyRole("ADMIN")
                		.requestMatchers(HttpMethod.DELETE, "/api/v1/roles/**").hasAnyRole("ADMIN")
						
//						.anyRequest().denyAll()
                		.anyRequest().authenticated()

 
                )
                .exceptionHandling(exception ->
        			exception
        				.authenticationEntryPoint(customAuthEntryPoint)
        		)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
 
 
}