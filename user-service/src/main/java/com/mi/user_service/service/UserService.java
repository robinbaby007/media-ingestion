package com.mi.user_service.service;

import com.mi.user_service.model.User;
import com.mi.user_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signup(String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already taken: " + email);
        }

        User user = User.builder()
                .email(email)
                .password(password)
                .build();

        return userRepository.save(user);
    }

    public User login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                return user; // Login success
            }
        }
        throw new IllegalArgumentException("Invalid email or password");
    }
}

