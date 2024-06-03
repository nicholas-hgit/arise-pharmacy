package com.arise.pharmacy.security.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    UserRepository userRepository;
    UserDetailsService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserDetailsServiceImpl(userRepository);
    }

    @Test
    void loadUserByUsernameReturnsUser() {

        //GIVEN
        String mail = "user@gmail.com";

        User user = User.builder()
                .email(mail)
                .password("password")
                .enabled(true)
                .build();

        given(userRepository.findByEmail(mail)).willReturn(Optional.of(user));

        //WHEN
        User expected = (User) underTest.loadUserByUsername(mail);

        //THEN
        assertThat(expected).isEqualTo(user);
    }

    @Test
    void loadUserByUsernameThrowsException(){

        //GIVEN
        String mail = "usernotexist@gmail.com";

        //WHEN
        //THEN
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> underTest.loadUserByUsername(mail))
                .withMessage("User not found");
    }
}