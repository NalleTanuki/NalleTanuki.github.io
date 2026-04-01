package com.ipartek.componentes;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
									//256 BYTES
	private final String SECRET_KEY = "12345678901234567890123456789012_ABCDEFG";

	public String generateToken(String username, String role) {
	return Jwts.builder()
			.setSubject(username)
			.claim("rol", role)
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000)) // 60 minutos
			.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
			.compact();
	 }
	 
	public Claims extractClaims(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
			.build()
			.parseClaimsJws(token)
			.getBody();
	}
	 	
	public boolean isTokenValid(String token) {
		try {
			Claims claims = extractClaims(token);
			return claims.getExpiration().after(new Date());
		} catch (Exception e) {
			return false;
		}
	}
}
