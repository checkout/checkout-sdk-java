package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.applepay;

import com.google.gson.annotations.SerializedName;
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
    @SerializedName("ephemeral_public_key")
    private String ephemeralPublicKey;

    /**
     * The hash of the X.509 encoded public key bytes of the merchant's certificate.
     * [Optional]
     */
    @SerializedName("public_key_hash")
    private String publicKeyHash;

    /**
     * The transaction identifier, generated on the device.
     * [Optional]
     */
    @SerializedName("transaction_id")
    private String transactionId;
}
