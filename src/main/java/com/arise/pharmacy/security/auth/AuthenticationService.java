package com.arise.pharmacy.security.auth;

public interface AuthenticationService {
    String authenticate(AuthenticationRequest request);
}
