package com.arise.pharmacy.products;

import java.util.Objects;

public record ProductRequest(String name, String desc, Double price, String image) {
    public ProductRequest {
        Objects.requireNonNull(name);
        Objects.requireNonNull(desc);
        Objects.requireNonNull(price);
        Objects.requireNonNull(image);
    }
}
