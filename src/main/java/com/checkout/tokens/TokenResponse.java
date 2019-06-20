package com.checkout.tokens;

import com.checkout.common.Resource;

import java.time.Instant;

public class TokenResponse extends Resource {
    private String type;
    private String token;
    private Instant expiresOn;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(Instant expiresOn) {
        this.expiresOn = expiresOn;
    }
}