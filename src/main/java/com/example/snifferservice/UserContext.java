package com.example.snifferservice;

import org.springframework.stereotype.Component;

@Component
public class UserContext {
    public static final String AUTH_TOKEN= "tmx-auth-token";
    private String authToken = new String();

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
