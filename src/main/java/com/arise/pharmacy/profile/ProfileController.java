package com.arise.pharmacy.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.status(CREATED).body(savedProfile);
    }

    @GetMapping
    public ResponseEntity<?> getProfileByEmail(@RequestParam String email){

        Profile profile;
        try {
            profile = profileService.findProfileByEmail(email);
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.status(OK).body(profile);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<?> getProfileById(@PathVariable Long id){

        Profile profile;
        try {
            profile = profileService.findProfileById(id);
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.status(OK).body(profile);
    }

    @PutMapping(path = "update")
    public ResponseEntity<?> updateProfile(@RequestBody ProfileRequest profile){

        Profile updatedProfile;
        try {
            updatedProfile = profileService.updateProfile(profile);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.status(OK).body(updatedProfile);
    }
}
