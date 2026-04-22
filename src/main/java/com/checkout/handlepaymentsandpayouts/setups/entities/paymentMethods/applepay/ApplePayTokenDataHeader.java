package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.applepay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Additional version-dependent information used to decrypt and verify the Apple Pay payment.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ApplePayTokenDataHeader {

    /**
     * The ephemeral public key bytes.
     * [Optional]
     */
    private String ephemeralPublicKey;

    /**
     * The hash of the X.509 encoded public key bytes of the merchant's certificate.
     * [Optional]
     */
    private String publicKeyHash;

    /**
     * The transaction identifier, generated on the device.
     * [Optional]
     */
    private String transactionId;
}
