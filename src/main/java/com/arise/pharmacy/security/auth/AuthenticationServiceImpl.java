package com.arise.pharmacy.security.auth;

import com.arise.pharmacy.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public String authenticate(AuthenticationRequest request) {

        var authToken = UsernamePasswordAuthenticationToken.unauthenticated(
                request.email(),
                request.password()
        );

        var authResults = authenticationManager.authenticate(authToken);

        return jwtService.generateToken(
                authResults.getName(),
                authResults.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
        );
    }
}
