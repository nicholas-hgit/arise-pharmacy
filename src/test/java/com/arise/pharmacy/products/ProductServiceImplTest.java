package com.arise.pharmacy.products;

import com.arise.pharmacy.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    ProductRepository repository;
    @Mock
    ProductRequest request;
    ProductService underTest;

    @BeforeEach
    void setUp(){
        underTest = new ProductServiceImpl(repository);
    }

    @Test
    void saveProduct() {

        //GIVEN
        Product product = Product.builder()
                .name("Apples")
                .description("3 red apples")
                .price(25.00)
                .build();

        given(request.name()).willReturn(product.getName());
        given(request.desc()).willReturn(product.getDescription());
        given(request.price()).willReturn(product.getPrice());

        //WHEN
        underTest.saveProduct(request);

        //THEN
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        then(repository).should().save(captor.capture());
        assertThat(captor.getValue()).hasFieldOrPropertyWithValue("name",product.getName())
                .hasFieldOrPropertyWithValue("description",product.getDescription())
                .hasFieldOrPropertyWithValue("price",product.getPrice());
    }

    @Test
    void updateProduct() {

        //GIVEN
        Product product = Product.builder()
                .id(1L)
                .name("Apples")
                .description("3 red apples")
                .price(25.00)
                .build();

        Product updatedProduct = Product.builder()
                        .id(1L)
                        .name("Apples")
                        .description("2 red apples")
                        .price(12.00)
                        .build();


        given(request.name()).willReturn(product.getName());
        given(request.desc()).willReturn("2 red apples");
        given(request.price()).willReturn(12.00);
        given(repository.findById(1L)).willReturn(Optional.of(product));

        //WHEN
        underTest.updateProduct(1L,request);

        //THEN
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        then(repository).should().save(captor.capture());
        assertThat(captor.getValue()).isEqualTo(product)
                .hasFieldOrPropertyWithValue("name",updatedProduct.getName())
                .hasFieldOrPropertyWithValue("description",updatedProduct.getDescription())
                .hasFieldOrPropertyWithValue("price",updatedProduct.getPrice());

    }

    @Test
    void updateProductThrows(){

        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.empty());

        //WHEN
        //THEN
        assertThatExceptionOfType(ProductNotFoundException.class).isThrownBy(() -> underTest.updateProduct(1L,request))
                .withMessage("product with id: 1 not found");
    }

    @Test
    void findProductById(){

        //GIVEN
        Long id = 1L;
        Product product = Product.builder()
                .id(id)
                .name("Apples")
                .description("3 red apples")
                .price(25.00)
                .build();
        
        given(repository.findById(id)).willReturn(Optional.of(product));

        //WHEN
        Product expected = underTest.findProductById(id);

        //THEN
         assertThat(product).isEqualTo(expected);
         
    }

    @Test
    void findByProductIdThrows(){

        //GIVEN
        Long id = 1L;
        given(repository.findById(id)).willReturn(Optional.empty());

        //WHEN
        //THEN
        assertThatExceptionOfType(ProductNotFoundException.class).isThrownBy(() -> underTest.findProductById(id))
                .withMessage("product with id: 1 not found");
    }

    @Test
    void findAllProducts() {

        //GIVEN
        //WHEN
        underTest.findAllProducts();

        //THEN
        then(repository).should().findAll();
    }
}