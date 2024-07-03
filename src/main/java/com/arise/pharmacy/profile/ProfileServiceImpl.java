package com.arise.pharmacy.profile;

import com.arise.pharmacy.security.user.User;
import com.arise.pharmacy.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    @Override
    public Profile findProfileById(Long id) throws UsernameNotFoundException {

        Optional<Profile> profile = profileRepository.findById(id);
        if(profile.isEmpty()){
            throw new UsernameNotFoundException("user not found");
        }

        return  profile.get();
    }

    @Override
    public Profile findProfileByEmail(String email) throws UsernameNotFoundException {

        Optional<Profile> profile = profileRepository.findByEmail(email);
        if(profile.isEmpty()){
            throw new UsernameNotFoundException("user not found");
        }

        return profile.get();
    }


    @Override
    public com.arise.pharmacy.profile.Profile saveProfile(ProfileRequest profile) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(profile.email());

        if(user.isEmpty()){
            throw new UsernameNotFoundException("user account must exist");
        }

        Profile profile1 = Profile.builder()
                .user(user.get())
                .firstName(profile.firstName())
                .lastName(profile.lastName())
                .phoneNumber(profile.phone())
                .build();

         profileRepository.save(profile1);

         return profile1;
    }

    @Override
    public Profile updateProfile(ProfileRequest updatedProfile) throws UsernameNotFoundException{

        Optional<Profile> profileOptional = profileRepository.findByEmail(updatedProfile.email());

        if(profileOptional.isEmpty()){
            throw new UsernameNotFoundException("user not found");
        }

        Profile profile = profileOptional.get();

        profile.setFirstName(updatedProfile.firstName());
        profile.setLastName(updatedProfile.lastName());
        profile.setPhoneNumber(updatedProfile.phone());

        profileRepository.save(profile);

        return profile;
    }
}
