package com.arise.pharmacy.profile;


public record ProfileRequest(String email,String id ,String firstName, String lastName, Long phone, String image) {
}
