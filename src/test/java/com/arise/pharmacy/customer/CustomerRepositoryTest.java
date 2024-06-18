package com.arise.pharmacy.customer;

import com.arise.pharmacy.security.user.User;
import com.arise.pharmacy.security.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static com.arise.pharmacy.security.roles.Role.SHOPPER;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomerRepository underTest;

    @Test
    void findByEmailReturnsCustomerDetails() {

        //GIVEN
        User user = User.builder()
                .email("john@gmail.com")
                .role(SHOPPER)
                .enabled(true)
                .password("@john2461")
                .build();

        Customer customer = Customer.builder()
                .user(user)
                .firstName("john")
                .lastName("doe")
                .phoneNumber(27_66_256_2346L)
                .build();

        userRepository.save(user);
        underTest.save(customer);

        //WHEN
        Optional<Customer> expected = underTest.findByEmail(user.getUsername());

        //THEN
        assertThat(expected).contains(customer);
    }

    @Test
    void findByEmailReturnsEmptyOptional(){

        //GIVEN
        String email = "userdoesntexist@gmail.com";

        //WHEN
        Optional<Customer> expected = underTest.findByEmail(email);

        //THEN
        assertThat(expected).isEmpty();

    }
}