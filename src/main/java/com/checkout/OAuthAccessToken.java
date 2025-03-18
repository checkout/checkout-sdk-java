package com.checkout;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Slf4j
final class OAuthAccessToken {

    private final String token;
    private final String tokenType;
    private final LocalDateTime expirationDate;

    OAuthAccessToken(final String token, final String tokenType, final LocalDateTime expirationDate) {
        this.token = token;
        this.tokenType = tokenType;
        this.expirationDate = expirationDate;
    }

    boolean isValid() {
        if (expirationDate == null) {
            log.warn("Token expiration date is null");
            return false;
        }
        boolean isValid = expirationDate.isAfter(now());
        if (!isValid) {
            log.debug("Token has expired at {}", expirationDate);
        } else if (expirationDate.isBefore(now().plusMinutes(5))) {
            log.debug("Token is about to expire at {}", expirationDate);
        }

        return isValid;
    }

    String getToken() {
        return token;
    }

    String getTokenType() {
        return tokenType;
    }

    LocalDateTime getExpirationDate() {
        return expirationDate;
    }

}
