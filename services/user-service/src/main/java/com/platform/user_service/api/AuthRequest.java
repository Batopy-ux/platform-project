package com.platform.user_service.api;

public record AuthRequest(String username, String email, String password){}