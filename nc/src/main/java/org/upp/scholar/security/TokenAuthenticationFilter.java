package org.upp.scholar.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.upp.scholar.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter
{
	private TokenUtils tokenUtils;
	private UserService userDetailsService;

	public TokenAuthenticationFilter(TokenUtils tokenUtils, UserService userDetailsService) {
		this.tokenUtils = tokenUtils;
		this.userDetailsService = userDetailsService;
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		final String authToken = this.tokenUtils.getToken(request);
		TokenAuthenticationFilter.log.info("Authentication token: {}", authToken);
		if (authToken != null) {
			final String username = this.tokenUtils.getUsernameFromToken(authToken);

			if (username != null) {
				TokenAuthenticationFilter.log.info("User to be authenticated: {}", username);
				final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

				if (this.tokenUtils.validateToken(authToken, userDetails)) {
					TokenAuthenticationFilter.log.info("Token is valid against authenticated user");
					final TokenBasedAuthentication authentication = new TokenBasedAuthentication(authToken, userDetails);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		}

		chain.doFilter(request, response);
	}
}