package com.platform.user_service.service;

import com.platform.user_service.domain.User;
import com.platform.user_service.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService{
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
        this.repo = userRepository;
    }

    public User register(String username, String email, String rawPassword){
        if(repo.findByUsername(username).isPresent()){
            throw new IllegalArgumentException("username exists");
        }
        User u = new User();
        u.setUsername(username);
        u.setEmail(email);
        u.setPasswordHash(passwordEncoder.encode(rawPassword));
        u.setRoles("ROLE_USER");
        return repo.save(u);
    }

    public User findByUsername(String username) {
        return repo.findByUsername(username).orElse(null);
    }

    public User findByEmail(String email) {
        return repo.findByEmail(email).orElse(null);
    }
}