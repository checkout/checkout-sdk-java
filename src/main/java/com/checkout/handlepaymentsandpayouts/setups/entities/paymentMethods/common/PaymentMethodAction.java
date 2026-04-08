package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Next-step action returned by the API for a payment method (read-only).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentMethodAction {

    /**
     * The type of action.
     * <p>[Read-only]</p>
     * {@code sdk} or {@code otp} (varies by payment method).
     */
    private String type;

    /**
     * The client token for the Klarna or PayPal SDK.
     * <p>[Read-only]</p>
     */
    @SerializedName("client_token")
    private String clientToken;

    /**
     * The session identifier for the Klarna payment method session.
     * <p>[Read-only]</p>
     */
    @SerializedName("session_id")
    private String sessionId;

    /**
     * The PayPal order ID to use with the PayPal SDK.
     * <p>[Read-only]</p>
     */
    @SerializedName("order_id")
    private String orderId;
}