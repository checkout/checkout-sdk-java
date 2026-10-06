package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.cashapp;

import com.google.gson.annotations.SerializedName;

/**
 * The type of next action for the Cash App payment method.
 */
public enum CashAppActionType {

    /**
     * Redirect the customer to Cash App to authorize the payment.
     */
    @SerializedName("redirect")
    REDIRECT
}
