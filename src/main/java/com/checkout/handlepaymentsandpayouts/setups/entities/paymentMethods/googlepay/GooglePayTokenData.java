package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.googlepay;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Google Pay payment token data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class GooglePayTokenData {

    /**
     * The encryption and signing scheme used to create this message. Defaults to ECv0.
     * [Optional]
     */
    @SerializedName("protocol_version")
    private String protocolVersion;

    /**
     * The signature that verifies the message came from Google, created using ECDSA.
     * [Optional]
     */
    private String signature;

    /**
     * A serialized JSON string containing the encryptedMessage, ephemeralPublicKey, and tag.
     * [Optional]
     */
    @SerializedName("signed_message")
    private String signedMessage;

    /**
     * The public key provided to the Google API in the tokenizationSpecification.parameters field.
     * [Optional]
     */
    @SerializedName("tokenization_key")
    private String tokenizationKey;
}
