package com.arise.pharmacy.security.register;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/users")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping(path = "register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request){
        try {
            registrationService.register(request);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(CREATED).build();
    }
}
