package com.arise.pharmacy.security.register;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/users")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping(path = "register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request){
        registrationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
