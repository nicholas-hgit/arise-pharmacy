package com.arise.pharmacy.profile;

import com.arise.pharmacy.security.user.User;
import com.arise.pharmacy.security.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ProfileServiceImplTest {

    @Mock
    ProfileRepository profileRepository;
    @Mock
    ProfileRequest request;
    @Mock
    UserRepository userRepository;

    ProfileService underTest;

    @BeforeEach
    void setUp(){
        underTest = new ProfileServiceImpl(profileRepository,userRepository);
    }

    @Test
    void findProfileById(){

        //GIVEN
        Long id = 1234L;
        Profile profile = Profile.builder()
                .id(id)
                .firstName("nick")
                .lastName("cage")
                .phoneNumber(2767_1312_787L)
                .build();

        given(profileRepository.findById(id)).willReturn(Optional.of(profile));

        //WHEN
        Profile expected = underTest.findProfileById(id);

        //THEN
        assertThat(profile).isEqualTo(expected);
    }

    @Test
    void findProfileByIdThrows(){

        //GIVEN
        given(profileRepository.findById(anyLong())).willReturn(Optional.empty());

        //WHEN
        //THEN
        assertThatExceptionOfType(UsernameNotFoundException.class).isThrownBy(() -> underTest.findProfileById(1L))
                .withMessage("user not found");
    }

    @Test
    void findProfileByEmail(){

        //GIVEN
        String email = "profile@gmail.com";
        Profile profile = Profile.builder()
                .id(1L)
                .firstName("nick")
                .lastName("cage")
                .phoneNumber(2767_1312_787L)
                .build();

        given(profileRepository.findByEmail(email)).willReturn(Optional.of(profile));

        //WHEN
        Profile expected = underTest.findProfileByEmail(email);

        //THEN
        assertThat(profile).isEqualTo(expected);
    }

    @Test
    void findProfileByEmailThrows(){

        //GIVEN
        String email = "user@gmail.com";
        given(profileRepository.findByEmail(anyString())).willReturn(Optional.empty());

        //WHEN
        //THEN
        assertThatExceptionOfType(UsernameNotFoundException.class).isThrownBy(() -> underTest.findProfileByEmail(email))
                .withMessage("user not found");
    }

    @Test
    void saveProfile() {

        //GIVEN
        given(request.email()).willReturn("user@gmail.com");
        given(request.firstName()).willReturn("john");
        given(request.lastName()).willReturn("doe");
        given(request.phone()).willReturn(2786_345_6787L);

        User user = User.builder()
                .email(request.email())
                .build();

        given(userRepository.findByEmail(request.email())).willReturn(Optional.of(user));

        Profile profile = Profile.builder()
                .user(user)
                .firstName("john")
                .lastName("doe")
                .phoneNumber(2786_345_6787L)
                .build();
        
        //WHEN
        underTest.saveProfile(request);

        //THEN
        ArgumentCaptor<Profile> captor = ArgumentCaptor.forClass(Profile.class);

        then(profileRepository).should().save(captor.capture());
        assertThat(captor.getValue()).hasFieldOrPropertyWithValue("firstName", profile.getFirstName())
                .hasFieldOrPropertyWithValue("lastName", profile.getLastName())
                .hasFieldOrPropertyWithValue("phoneNumber", profile.getPhoneNumber())
                .hasFieldOrPropertyWithValue("user",user);
    }

    @Test
    void saveProfileThrowsException(){

        //Given
        given(request.email()).willReturn("user@gmail.com");
        given(userRepository.findByEmail(anyString())).willReturn(Optional.empty());

        //WHEN
        //THEN
        assertThatExceptionOfType(UsernameNotFoundException.class).isThrownBy(() -> underTest.saveProfile(request))
                .withMessage("user account must exist");
    }

    @Test
    void updateProfile() {

        //GIVEN
        Profile profile = Profile.builder()
                .id(1L)
                .firstName("nick")
                .lastName("cage")
                .phoneNumber(2786_345_6787L)
                .build();

        Profile updatedProfile = Profile.builder()
                        .id(1L)
                        .firstName("john")
                        .lastName("doe")
                        .phoneNumber(profile.getPhoneNumber())
                        .build();

        given(request.email()).willReturn("user@gmail.com");
        given(request.firstName()).willReturn(updatedProfile.getFirstName());
        given(request.lastName()).willReturn(updatedProfile.getLastName());
        given(request.phone()).willReturn(updatedProfile.getPhoneNumber());
        given(profileRepository.findByEmail(request.email())).willReturn(Optional.of(profile));

        //WHEN
        underTest.updateProfile(request);

        //THEN
        ArgumentCaptor<Profile> captor = ArgumentCaptor.forClass(Profile.class);

        then(profileRepository).should().save(captor.capture());
        assertThat(captor.getValue()).isEqualTo(profile)
                .hasFieldOrPropertyWithValue("firstName", updatedProfile.getFirstName())
                .hasFieldOrPropertyWithValue("lastName", updatedProfile.getLastName())
                .hasFieldOrPropertyWithValue("phoneNumber", updatedProfile.getPhoneNumber());

    }

    @Test
    void updateProfileThrowsException(){

        //GIVEN
        given(request.email()).willReturn("user@gmail.com");
        given(profileRepository.findByEmail(anyString())).willReturn(Optional.empty());

        //WHEN
        //THEN
        assertThatExceptionOfType(UsernameNotFoundException.class).isThrownBy(() -> underTest.updateProfile(request))
                .withMessage("user not found");

    }
}