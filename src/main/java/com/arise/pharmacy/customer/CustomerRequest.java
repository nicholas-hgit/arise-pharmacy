package com.arise.pharmacy.customer;


public record CustomerRequest(String email, String firstName, String lastName, Long phone) {
}
