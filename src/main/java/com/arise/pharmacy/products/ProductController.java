package com.arise.pharmacy.products;

import com.arise.pharmacy.exceptions.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> saveProduct(@RequestBody ProductRequest product){

        Product savedProduct;
        try {
            savedProduct = productService.saveProduct(product);
        } catch (IllegalStateException e) {
            return  ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(CREATED).body(savedProduct);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductRequest product){

        Product updatedProduct;
        try {
            updatedProduct = productService.updateProduct(id,product);
        }catch (ProductNotFoundException | IllegalStateException e){
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.status(OK).body(updatedProduct);
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts(){

        List<Product> products = productService.findAllProducts();
        return ResponseEntity.status(OK).body(products);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){

        Product product;
        try {
            product = productService.findProductById(id);
        }catch (ProductNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.status(OK).body(product);
    }
}
