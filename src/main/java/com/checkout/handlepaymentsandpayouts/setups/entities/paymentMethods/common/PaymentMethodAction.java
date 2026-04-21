package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Next-step action returned by the API for a payment method.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentMethodAction {

    /**
     * The type of action: sdk or otp (varies by payment method).
     * [Optional]
     */
    private String type;

    /**
     * The client token for the Klarna or PayPal SDK.
     * [Optional]
     */
    @SerializedName("client_token")
    private String clientToken;

    /**
     * The session identifier for the Klarna payment method session.
     * [Optional]
     */
    @SerializedName("session_id")
    private String sessionId;

    /**
     * The PayPal order ID to use with the PayPal SDK.
     * [Optional]
     */
    @SerializedName("order_id")
    private String orderId;
}
