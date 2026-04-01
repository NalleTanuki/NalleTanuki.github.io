package com.ipartek.componentes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getServletPath();
		return path.startsWith("/swagger-ui") ||
				path.startsWith("/v3/api-docs") || 
				path.startsWith("/api-docs") ||
				path.startsWith("/swagger-resources") ||
				path.startsWith("/webjars") ||
				path.startsWith("/api/auth");
		// ||
//	path.equals("/api/v1/usuarios/validar/") ||
//	path.startsWith("/api/v1/usuarios/") ||              
//	path.equals("/login") ||
//	path.equals("/register");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
	        throws ServletException, java.io.IOException {

	    final String authHeader = request.getHeader("Authorization");
	    
        // PRINTS de PRUEBAAAAAAAAAAAAAAAAAAAAA
//        System.out.println("AUTH HEADER: " + authHeader);
	    

	    if (authHeader != null && authHeader.startsWith("Bearer ")) {
	        String token = authHeader.substring(7);

	        // PRINTS de PRUEBAAAAAAAAAAAAAAAAAAAAA
//	        System.out.println("TOKEN: " + token);
//	        System.out.println("VALIDO: " + jwtUtil.isTokenValid(token));
	        
	        if (jwtUtil.isTokenValid(token)) {
	            Claims claims = jwtUtil.extractClaims(token);
	            String username = claims.getSubject();
	            String rol = claims.get("rol", String.class);
	            
		        // PRINT de PRUEBAAAAAAAAAAAAAAAAAAAAA
//	            System.out.println("ROL DEL TOKEN: " + rol);

	            List<GrantedAuthority> authorities =
	                    List.of(new SimpleGrantedAuthority("ROLE_" + rol));

	            UsernamePasswordAuthenticationToken auth =
	                    new UsernamePasswordAuthenticationToken(username, null, authorities);

	            SecurityContextHolder.getContext().setAuthentication(auth);
	        }
	    }

	    chain.doFilter(request, response);
	}
}