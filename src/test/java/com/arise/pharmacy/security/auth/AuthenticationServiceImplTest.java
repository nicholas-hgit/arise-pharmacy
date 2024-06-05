package com.arise.pharmacy.security.auth;

import com.arise.pharmacy.security.jwt.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import  static  org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    AuthenticationRequest request;
    @Mock
    JwtService jwtService;

    AuthenticationService underTest;

    @BeforeEach
    void setUp() {
        underTest = new AuthenticationServiceImpl(authenticationManager, jwtService);
    }

    @Test
    void authenticate() {

        //GIVEN
        given(request.email()).willReturn("user@gmail.com");
        given(request.password()).willReturn("password");

        var authToken = UsernamePasswordAuthenticationToken.unauthenticated(
                request.email(),
                request.password()
        );

        var authResults = UsernamePasswordAuthenticationToken.authenticated(
                request.email(),
                request.password(),
                List.of()
        );

        given(authenticationManager.authenticate(authToken)).willReturn(authResults);
        given(jwtService.generateToken(eq(request.email()), anyList())).willReturn("fg".repeat(10));

        //WHEN
        String token = underTest.authenticate(request);

        //THEN
        assertThat("fg".repeat(10)).isEqualTo(token);
    }
}