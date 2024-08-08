package com.arise.pharmacy.profile;


import java.util.Objects;

public record ProfileRequest(String email, String id , String firstName, String lastName, Long phone, String image) {
    public ProfileRequest {
        Objects.requireNonNull(email);
        Objects.requireNonNull(id);
        Objects.requireNonNull(lastName);
        Objects.requireNonNull(phone);
        Objects.requireNonNull(image);
    }

    public boolean isNotValid(){
        return email.isBlank()
                && id.isBlank()
                && firstName.isBlank()
                && lastName.isBlank()
                && String.valueOf(phone).length() != 9;

    }
}
