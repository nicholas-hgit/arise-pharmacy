package com.arise.pharmacy.security.register;

import com.arise.pharmacy.security.user.User;
import com.arise.pharmacy.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;

    @Override
    public void register(RegistrationRequest request) {

        User user = User.builder()
                .email(request.email())
                .password(request.password())
                .enabled(true)
                .build();

        userRepository.save(user);

    }
}
