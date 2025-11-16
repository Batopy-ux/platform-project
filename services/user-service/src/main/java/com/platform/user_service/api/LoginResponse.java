package com.platform.user_service.api;

public class LoginResponse {
    private String token;

    private String tokeType = "Bearer";

    public LoginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokeType;
    }

    public void setTokenType(String tokeType) {
        this.tokeType = tokeType;
    }
}
