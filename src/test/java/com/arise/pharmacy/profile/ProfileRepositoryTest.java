package com.arise.pharmacy.profile;

import com.arise.pharmacy.security.user.User;
import com.arise.pharmacy.security.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static com.arise.pharmacy.security.roles.Role.SHOPPER;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class ProfileRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProfileRepository underTest;

    @Test
    void findByEmailReturnsProfileDetails() {

        //GIVEN
        User user = User.builder()
                .email("john@gmail.com")
                .role(SHOPPER)
                .enabled(true)
                .password("@john2461")
                .build();

        Profile profile = Profile.builder()
                .user(user)
                .identityNumber("1234567890128")
                .firstName("john")
                .lastName("doe")
                .phoneNumber(27_66_256_2346L)
                .build();

        userRepository.save(user);
        underTest.save(profile);

        //WHEN
        Optional<Profile> expected = underTest.findByEmail(user.getUsername());

        //THEN
        assertThat(expected).contains(profile);
    }

    @Test
    void findByEmailReturnsEmptyOptional(){

        //GIVEN
        String email = "userdoesntexist@gmail.com";

        //WHEN
        Optional<Profile> expected = underTest.findByEmail(email);

        //THEN
        assertThat(expected).isEmpty();

    }
}