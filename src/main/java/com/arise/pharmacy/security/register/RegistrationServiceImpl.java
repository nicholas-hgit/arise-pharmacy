package com.arise.pharmacy.security.register;

import static com.arise.pharmacy.security.roles.Role.STAFF;
import static com.arise.pharmacy.security.roles.Role.SHOPPER;

import com.arise.pharmacy.security.roles.Role;
import com.arise.pharmacy.security.user.User;
import com.arise.pharmacy.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegistrationRequest request) {

        Role role = request.role().equalsIgnoreCase("staff")? STAFF : SHOPPER;

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(role)
                .enabled(true)
                .build();

        userRepository.save(user);

    }
}
