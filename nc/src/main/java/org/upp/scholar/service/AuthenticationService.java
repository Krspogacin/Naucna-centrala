package org.upp.scholar.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.upp.scholar.security.AuthenticationRequest;
import org.upp.scholar.security.TokenUtils;
import org.upp.scholar.security.UserState;

import javax.ws.rs.NotFoundException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class AuthenticationService {

    private TokenUtils tokenUtils;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(final TokenUtils tokenUtils, final AuthenticationManager authenticationManager) {
        this.tokenUtils = tokenUtils;
        this.authenticationManager = authenticationManager;
    }

    public UserState createAuthenticationToken(final AuthenticationRequest authenticationRequest) throws AuthenticationException {
        Assert.notNull(authenticationRequest, "Authentication request object can't be null!");
        Assert.noNullElements(Stream.of(authenticationRequest.getUsername(), authenticationRequest.getPassword()).toArray(),
                "Both username and password must be set!");

        AuthenticationService.log.info("Begin authentication for username '{}'", authenticationRequest.getUsername());

        final UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final Authentication authentication;
        try {
            authentication = this.authenticationManager.authenticate(authenticationToken);
        } catch (final AuthenticationException e) {
            throw new NotFoundException();
        }

        AuthenticationService.log.info("User with username '{}' is authenticated successfully", authenticationRequest.getUsername());

        final String token = this.tokenUtils.generateToken(authenticationRequest.getUsername());


        AuthenticationService.log.info("Completed authentication for username '{}'", authenticationRequest.getUsername());

        return UserState.builder()
                .username(authenticationRequest.getUsername())
                .token(token)
                .roles(authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .build();
    }
}
