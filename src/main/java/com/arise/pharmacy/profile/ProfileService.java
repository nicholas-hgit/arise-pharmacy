package com.arise.pharmacy.profile;

import com.arise.pharmacy.exceptions.InvalidIdentityNumberException;
import com.arise.pharmacy.exceptions.ProfileNotFoundException;

public interface ProfileService {

    Profile saveProfile(ProfileRequest profile) throws InvalidIdentityNumberException,IllegalStateException;
    Profile updateProfile(ProfileRequest updatedProfile);
    Profile findProfileById(Long id) throws ProfileNotFoundException;
    Profile findProfileByEmail(String email);
}
