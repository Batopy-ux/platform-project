package com.platform.user_service.api;

import com.platform.user_service.domain.User;
import com.platform.user_service.service.UserService;
import com.platform.user_service.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService, JwtProvider jwtProvider, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest req){
        User user = userService.register(req.getUsername(), req.getEmail(), req.getPassword());
        return ResponseEntity.ok("Registered");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authLogin(@RequestBody LoginRequest req){
        var user = userService.findByUsername(req.getUsername());
        if(user == null) return ResponseEntity.status(404).build();
        if(!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())){
            return ResponseEntity.status(401).build();
        }
        String token = jwtProvider.generateToken(user.getUsername());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
