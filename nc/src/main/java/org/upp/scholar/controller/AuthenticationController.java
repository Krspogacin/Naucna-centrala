package org.upp.scholar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.upp.scholar.security.AuthenticationRequest;
import org.upp.scholar.security.UserState;
import org.upp.scholar.service.AuthenticationService;

@RestController
public class AuthenticationController 
{

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserState> createAuthenticationToken(@RequestBody final AuthenticationRequest authenticationRequest)
			throws AuthenticationException {
		return ResponseEntity.ok(this.authenticationService.createAuthenticationToken(authenticationRequest));
	}
}