package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common;

import com.google.gson.annotations.SerializedName;

public enum PaymentMethodInitialization {
    /**
     * The payment method is disabled
     */
    @SerializedName("disabled")
    DISABLED,
    
    /**
     * The payment method is enabled
     */
    @SerializedName("enabled")
    ENABLED
}