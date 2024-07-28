package com.masjid.crm.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

	@Value("${jwt.expiration}")
	private Long expiration;

	@Value("${jwt.secret}")
	private String secret;

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey("mykare-admin").parseClaimsJws(token).getBody();
	}

	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, username);
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {

		Date now = new Date();
		Date expirationDate = new Date(now.getTime() + expiration * 1000); // Convert seconds to milliseconds

		JwtBuilder jwtBuilder = Jwts.builder();
		jwtBuilder.setClaims(claims);
		jwtBuilder.setSubject(subject);
		jwtBuilder.setIssuedAt(new Date(System.currentTimeMillis()));
		jwtBuilder.setExpiration(expirationDate);
		jwtBuilder.signWith(SignatureAlgorithm.HS512, secret);
		return jwtBuilder.compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return username.equals(userDetails.getUsername());
	}
}
