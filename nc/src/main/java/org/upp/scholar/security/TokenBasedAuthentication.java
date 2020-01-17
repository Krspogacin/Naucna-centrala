package org.upp.scholar.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class TokenBasedAuthentication extends AbstractAuthenticationToken 
{
	private String token;
	private final UserDetails user;

	public TokenBasedAuthentication(String token, UserDetails user) {
		super(user.getAuthorities());
		this.token = token;
		this.user = user;
	}

	@Override
	public boolean isAuthenticated() {
		return true;
	}

	@Override
	public Object getCredentials() {
		return token;
	}

	@Override
	public UserDetails getPrincipal() {
		return user;
	}
}