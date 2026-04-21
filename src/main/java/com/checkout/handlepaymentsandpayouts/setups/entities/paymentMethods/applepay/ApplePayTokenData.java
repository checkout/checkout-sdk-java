package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.applepay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Apple Pay encrypted payment token data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ApplePayTokenData {

    /**
     * The version of the payment token: EC_v1 for ECC-encrypted data, RSA_v1 for RSA-encrypted data.
     * [Optional]
     */
    private String version;

    /**
     * The encrypted payment data, Base64-encoded.
     * [Optional]
     */
    private String data;

    /**
     * The signature of the payment and header data.
     * [Optional]
     */
    private String signature;

    /**
     * Additional version-dependent information used to decrypt and verify the payment.
     * [Optional]
     */
    private ApplePayTokenDataHeader header;
}
