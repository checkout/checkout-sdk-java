package com.checkout;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

final class OAuthAccessToken {

    private static final long EXPIRATION_THRESHOLD_SECONDS = 10;

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
        return expirationDate.isAfter(now().minusSeconds(EXPIRATION_THRESHOLD_SECONDS));
    }

    String getToken() {
        return token;
    }

    LocalDateTime getExpirationDate() {
        return expirationDate;
    }

}
