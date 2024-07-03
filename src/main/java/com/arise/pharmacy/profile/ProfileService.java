package com.arise.pharmacy.profile;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface ProfileService {

    Profile saveProfile(ProfileRequest profile) throws UsernameNotFoundException;
    Profile updateProfile(ProfileRequest updatedProfile) throws UsernameNotFoundException;
    Profile findProfileById(Long id) throws UsernameNotFoundException ;
    Profile findProfileByEmail(String email) throws UsernameNotFoundException;
}
