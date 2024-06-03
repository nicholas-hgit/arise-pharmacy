package com.arise.pharmacy.security.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository underTest;

    @Test
    void findByEmailReturnsUser() {

        //GIVEN
        User user = User.builder()
                .email("user@gmail.com")
                .password("password")
                .enabled(true)
                .build();

        underTest.save(user);

        //WHEN
        Optional<User> expected = underTest.findByEmail("user@gmail.com");

        //THEN
        assertThat(expected).isNotEmpty()
                            .containsSame(user);

    }

    @Test
    void findByEmailReturnsEmptyOptional(){

        //GIVEN
        String email = "usernotexist@gmail.com";

        //WHEN
        Optional<User> expected = underTest.findByEmail(email);

        //THEN
        assertThat(expected).isEmpty();
    }
}