package org.upp.scholar.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils 
{
	@Value("${spring.application.name}")
	private String APP_NAME;

	@Value("${scientific-center.secret}")
	public String SECRET;

	@Value("${scientific-center.expires-in}")
	private int TOKEN_EXPIRES_IN;
	
	@Value("${scientific-center.auth-header}")
	private String AUTH_HEADER;

	@Value("${scientific-center.auth-header-prefix}")
	private String AUTH_HEADER_PREFIX;



	private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

	public String generateToken(String username) 
	{
		return Jwts.builder()
				.setIssuer(this.APP_NAME)
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(this.generateExpirationDate(this.TOKEN_EXPIRES_IN))
				.signWith(TokenUtils.SIGNATURE_ALGORITHM, this.SECRET).compact();
	}

	private Date generateExpirationDate(int expiresIn)
	{
		return new Date(new Date().getTime() + expiresIn * 1000);
	}

	public String refreshToken(String token) 
	{
		String refreshedToken;
		try 
		{
			Claims claims = this.getAllClaimsFromToken(token);
			claims.setIssuedAt(new Date());
			refreshedToken = Jwts.builder()
					.setClaims(claims)
					.setExpiration(this.generateExpirationDate(this.TOKEN_EXPIRES_IN))
					.signWith(TokenUtils.SIGNATURE_ALGORITHM, this.SECRET).compact();
		}
		catch (Exception e) 
		{
			refreshedToken = null;
		}
		return refreshedToken;
	}

	public Boolean canTokenBeRefreshed(String token)
	{
		Date expiration = this.getExpirationDateFromToken(token);
		return !expiration.before(new Date());
	}

	public Boolean validateToken(String token, UserDetails userDetails) 
	{
		String username = this.getUsernameFromToken(token);
		Date expirationDate = this.getExpirationDateFromToken(token);
		return (username != null && username.equals(userDetails.getUsername()) && expirationDate.after(new Date()));
	}

	private Claims getAllClaimsFromToken(String token) 
	{
		Claims claims;
		try
		{
			claims = Jwts.parser()
					.setSigningKey(this.SECRET)
					.parseClaimsJws(token)
					.getBody();
		}
		catch (Exception e) 
		{
			claims = null;
		}
		return claims;
	}

	public String getUsernameFromToken(String token) 
	{
		String username;
		try 
		{
			final Claims claims = this.getAllClaimsFromToken(token);
			username = claims.getSubject();
		}
		catch (Exception e) 
		{
			username = null;
		}
		return username;
	}

	public Date getExpirationDateFromToken(String token) 
	{
		Date expiration;
		try 
		{
			Claims claims = this.getAllClaimsFromToken(token);
			expiration = claims.getExpiration();
		}
		catch (Exception e)
		{
			expiration = null;
		}
		return expiration;
	}

	public String getToken(HttpServletRequest request)
	{
		String authHeader = request.getHeader(this.AUTH_HEADER);

		if (authHeader != null && authHeader.startsWith(this.AUTH_HEADER_PREFIX.concat(" ")))
		{
			return authHeader.substring(this.AUTH_HEADER_PREFIX.length() + 1);
		}

		return null;
	}
}