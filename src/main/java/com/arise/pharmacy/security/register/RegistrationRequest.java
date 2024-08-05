package com.arise.pharmacy.security.register;

import java.util.Objects;

public record RegistrationRequest(String email, String password, String role) {
    public RegistrationRequest {
        Objects.requireNonNull(email);
        Objects.requireNonNull(password);
        Objects.requireNonNull(role);
    }

    public boolean isNotValid(){
        return email.isBlank() && password.isBlank();

    }
}
