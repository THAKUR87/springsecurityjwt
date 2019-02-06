package com.demo.spring.springsecurity.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 507105159200353215L;
	
	static final String CLAIM_KEY_USERNAME = "sub";
	static final String CLAIM_KEY_AUDIENCE = "audience";
	static final String CLAIM_KEY_CREATED = "created";
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;

	public String getUsernameFromToken(String authToken) {
		String username = null;
		try {
			final Claims claims = getClaimsFromToken(authToken);
			username = claims.getSubject();
		} catch(Exception e ) {
			username = null;
		}
		
		return username;
	}
	
	private Claims getClaimsFromToken(String token) {
		Claims claims = null;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch(Exception e){
			claims = null;
		}
		return claims;
		
	}

	public boolean validateToken(String authToken, UserDetails userDetails) {
		JwtUser user = (JwtUser)userDetails;
		final String username = getUsernameFromToken(authToken);
		return (username.equals(user.getUsername()) && ! isTokenExpired(authToken));
	}
	
	private boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDatefromToken(token);
		return expiration.before(new Date());
	}

	private Date getExpirationDatefromToken(String token) {
		Date expiration = null;
		try {
			final Claims claims= getClaimsFromToken(token);
			if (claims != null ) {
				expiration = claims.getExpiration();
			} else {
				expiration = null;
			}
		} catch(Exception e) {
			expiration = null;
		}
		return expiration;
	}

	public String generateToken(JwtUser userDetails) {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		claims.put(CLAIM_KEY_CREATED, new Date());
		return generateClaims(claims);
	}

	private String generateClaims(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate()).signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}
}
