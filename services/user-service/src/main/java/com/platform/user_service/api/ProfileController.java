package com.platform.user_service.api;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {
    @GetMapping("/api/profile")
    public Object me(@AuthenticationPrincipal UserDetails userDetails){
        return java.util.Map.of(
                "username", userDetails.getUsername(),
                "email", userDetails.getAuthorities()
        );
    }
}
