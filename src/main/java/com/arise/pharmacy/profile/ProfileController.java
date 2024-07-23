package com.arise.pharmacy.profile;

import com.arise.pharmacy.exceptions.InvalidIdentityNumberException;
import com.arise.pharmacy.exceptions.ProfileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/info/user")
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping
    public ResponseEntity<?> saveProfile(@RequestBody ProfileRequest profile){

        Profile savedProfile;
        try {
            savedProfile = profileService.saveProfile(profile);
        } catch (InvalidIdentityNumberException e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.status(CREATED).body(savedProfile);
    }

    @GetMapping
    public ResponseEntity<?> getProfileByEmail(@RequestParam String email){

        Profile profile = profileService.findProfileByEmail(email);
        return ResponseEntity.status(OK).body(profile);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<?> getProfileById(@PathVariable Long id){

        Profile profile;
        try {
            profile = profileService.findProfileById(id);
        }catch (ProfileNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.status(OK).body(profile);
    }

    @PutMapping(path = "update")
    public ResponseEntity<?> updateProfile(@RequestBody ProfileRequest profile){

        Profile updatedProfile = profileService.updateProfile(profile);
        return ResponseEntity.status(OK).body(updatedProfile);
    }
}
