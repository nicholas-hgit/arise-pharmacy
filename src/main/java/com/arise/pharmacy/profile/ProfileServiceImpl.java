package com.arise.pharmacy.profile;

import com.arise.pharmacy.exceptions.InvalidIdentityNumberException;
import com.arise.pharmacy.exceptions.ProfileNotFoundException;
import com.arise.pharmacy.security.user.User;
import com.arise.pharmacy.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Profile findProfileById(Long id) throws ProfileNotFoundException {

        Optional<Profile> profile = profileRepository.findById(id);
        if(profile.isEmpty()){
            throw new ProfileNotFoundException("user not found");
        }

        return  profile.get();
    }

    @Override
    @PreAuthorize("authentication.name.equals(#email)")
    public Profile findProfileByEmail(String email){

        Optional<Profile> profile = profileRepository.findByEmail(email);
        //email already validated
        //noinspection OptionalGetWithoutIsPresent
        return profile.get();
    }

    @Override
    @PreAuthorize("authentication.name.equals(#profile.email())")
    public Profile saveProfile(ProfileRequest profile) throws InvalidIdentityNumberException {

        Optional<User> user = userRepository.findByEmail(profile.email());

        //noinspection OptionalGetWithoutIsPresent
        Profile profile1 = Profile.builder()
              .user(user.get())
              .identityNumber(profile.id())
              .firstName(profile.firstName())
              .lastName(profile.lastName())
              .phoneNumber(profile.phone())
              .image(profile.image())
              .build();

        profileRepository.save(profile1);

        return profile1;
    }

    @Override
    @PreAuthorize("authentication.name.equals(#updatedProfile.email())")
    public Profile updateProfile(ProfileRequest updatedProfile){

        Optional<Profile> profileOptional = profileRepository.findByEmail(updatedProfile.email());

        //noinspection OptionalGetWithoutIsPresent
        Profile profile = profileOptional.get();

        profile.setFirstName(updatedProfile.firstName());
        profile.setLastName(updatedProfile.lastName());
        profile.setPhoneNumber(updatedProfile.phone());
        profile.setImage(updatedProfile.image());

        profileRepository.save(profile);

        return profile;
    }
}
