package com.platform.user_service.api;

import com.platform.user_service.service.UserService;
import com.platform.user_service.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
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
    public ResponseEntity<String> authRegister(@RequestBody AuthRequest req){
        userService.register(req.username(), req.email(), req.password());
        return ResponseEntity.ok("Registered");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authLogin(@RequestBody AuthRequest req){
        var user = userService.findByUsername(req.username());
        if(user == null) return ResponseEntity.status(404).build();
        if(!passwordEncoder.matches(req.password(), user.getPasswordHash())){
            return ResponseEntity.status(503).build();
        }
        String token = jwtProvider.generateToken(user.getUsername());
        return ResponseEntity.ok(new AuthResponse(token, "Bearer"));
    }
}
