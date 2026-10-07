package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.cashapp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The next available action for the Cash App payment method.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CashAppAction {

    /**
     * The type of action.
     * [Optional] readOnly
     * Enum: "redirect"
     */
    private CashAppActionType type;

    /**
     * The URL to redirect the customer to so they can authorize the payment with Cash App.
     * [Optional] readOnly
     * Format: uri
     */
    private String redirectUrl;
}
