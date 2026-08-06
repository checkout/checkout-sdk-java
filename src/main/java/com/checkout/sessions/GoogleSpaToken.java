package com.checkout.sessions;

import lombok.Data;

/**
 * Token for the given PAN provisioned and authenticated.
 * <p>
 * Used by {@link GoogleSpaInfo#getToken()}.
 */
@Data
public final class GoogleSpaToken {

    /**
     * Value of token, represented as a numerical string.
     * [Optional]
     */
    private String number;

    /**
     * Expiry month of the token.
     * [Optional]
     */
    private Integer expiryMonth;

    /**
     * Expiry year of the token.
     * [Optional]
     */
    private Integer expiryYear;

}
