package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * The Checkout.com token of an Apple Pay or Google Pay payment method.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class WalletToken {

    /**
     * The token identifier, prefixed by tok_.
     * [Required]
     * Pattern: ^(tok)_(\w{26})$
     */
    private String id;

    /**
     * The date and time the token expires.
     * [Optional]
     * Format: date-time (ISO 8601)
     */
    private Instant expiresOn;
}
