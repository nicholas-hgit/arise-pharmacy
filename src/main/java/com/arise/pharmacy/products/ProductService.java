package com.arise.pharmacy.products;

import com.arise.pharmacy.exceptions.ProductNotFoundException;

import java.util.List;

public interface ProductService {

    Product saveProduct(ProductRequest product);
    Product updateProduct(Long id, ProductRequest newProduct) throws ProductNotFoundException;
    Product findProductById(Long id) throws ProductNotFoundException;
    List<Product> findAllProducts();

}
