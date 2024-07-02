package com.arise.pharmacy.products;

import com.arise.pharmacy.exceptions.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @PreAuthorize("hasAuthority('product:write')")
    public Product saveProduct(ProductRequest product) {

        Product newProduct = Product.builder()
                .name(product.name())
                .description(product.desc())
                .price(product.price())
                .imgURL(product.img())
                .build();

        productRepository.save(newProduct);

        return newProduct;
    }

    @Override
    @PreAuthorize("hasAuthority('product:write')")
    public Product updateProduct(Long id, ProductRequest newProduct) throws ProductNotFoundException {

        Optional<Product> productOptional = productRepository.findById(id);

        if(productOptional.isEmpty()){
            throw new ProductNotFoundException("product with id: %d not found".formatted(id));
        }

        Product product = productOptional.get();

        product.setName(newProduct.name());
        product.setDescription(newProduct.desc());
        product.setPrice(newProduct.price());
        product.setImgURL(newProduct.img());

        productRepository.save(product);

        return product;
    }

    @Override
    public Product findProductById(Long id) throws ProductNotFoundException {

        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isEmpty()){
            throw new ProductNotFoundException("product with id: %d not found".formatted(id));
        }

        return productOptional.get();
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
}
