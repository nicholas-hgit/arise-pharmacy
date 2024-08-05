package com.arise.pharmacy.security.register;

import com.arise.pharmacy.security.user.User;
import com.arise.pharmacy.security.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.arise.pharmacy.security.roles.Role.STAFF;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceImplTest {

    @Mock UserRepository userRepository;
    @Mock RegistrationRequest request;
    @Mock PasswordEncoder encoder;
    RegistrationService underTest;

    @BeforeEach
    void setUp(){
        underTest = new RegistrationServiceImpl(userRepository,encoder);
    }

    @Test
    void register() {

        //GIVEN
        User user = User.builder()
                .email("user@gmail.com")
                .password("password")
                .role(STAFF)
                .enabled(true)
                .build();

        given(request.email()).willReturn(user.getUsername());
        given(request.password()).willReturn(user.getPassword());
        given(request.role()).willReturn(STAFF.name());
        given(encoder.encode(any())).willReturn(user.getPassword());

        //WHEN
        underTest.register(request);

        //THEN
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        then(userRepository).should().save(captor.capture());
        assertThat(user).isEqualTo(captor.getValue());
    }
}