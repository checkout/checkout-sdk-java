package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Payment method action configuration
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodAction {

    /**
     * The type of action to be performed with the payment method
     */
    private String type;

    /**
     * The client token for payment method authentication
     */
    private String clientToken;

    /**
     * The session identifier for the payment method session
     */
    private String sessionId;
}