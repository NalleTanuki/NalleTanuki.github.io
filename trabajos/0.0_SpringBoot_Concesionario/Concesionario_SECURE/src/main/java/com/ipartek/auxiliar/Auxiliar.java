package com.ipartek.auxiliar;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Auxiliar {

	public static String generarSal(int bytes) {
		SecureRandom random = new SecureRandom();
		byte[] saltBytes = new byte[bytes];
		random.nextBytes(saltBytes);

		StringBuilder sb = new StringBuilder();
		for (byte b : saltBytes) {
			sb.append(String.format("%02X", b));
		}
		return sb.toString();
	}

	// SHA256 de una entrada de texto
	public static String hashear(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));

			StringBuilder sb = new StringBuilder(digest.length * 2);
			for (byte b : digest) {
				sb.append(String.format("%02x", b));
			}

			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("SHA-256 no disponible", e);
		}
	}
	
	
	
	public static String hashearPasswords(String pass, String salt) {
		String hash1 = hashear(pass + "Kolitza");
		
		return hashear(hash1 + salt);
	}

}
