package com.checkout;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

final class OAuthAccessToken {

    private final String token;
    private final LocalDateTime expirationDate;

    OAuthAccessToken(final String token, final LocalDateTime expirationDate) {
        this.token = token;
        this.expirationDate = expirationDate;
    }

    boolean isValid() {
        if (expirationDate == null) {
            return false;
        }
        return expirationDate.isAfter(now());
    }

    String getToken() {
        return token;
    }

    LocalDateTime getExpirationDate() {
        return expirationDate;
    }

}
