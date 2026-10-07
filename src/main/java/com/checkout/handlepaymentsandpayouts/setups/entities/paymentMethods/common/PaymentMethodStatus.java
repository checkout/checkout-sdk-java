package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common;

import com.google.gson.annotations.SerializedName;

/**
 * The payment method status.
 * Enum: "unavailable" "action_required" "ready" "initialization_required" "invalid"
 */
public enum PaymentMethodStatus {

    /**
     * The payment method is not available for this payment setup.
     */
    @SerializedName("unavailable")
    UNAVAILABLE,

    /**
     * The payment method needs an action before it can be confirmed, for example a redirect.
     */
    @SerializedName("action_required")
    ACTION_REQUIRED,

    /**
     * Not defined by the current API specification. Kept for backward compatibility.
     */
    @SerializedName("pending")
    PENDING,

    /**
     * The payment method is ready to be confirmed.
     */
    @SerializedName("ready")
    READY,

    /**
     * Not defined by the current API specification. Kept for backward compatibility.
     */
    @SerializedName("available")
    AVAILABLE,

    /**
     * The payment method must be initialized, by setting its initialization to enabled, before
     * it can be used.
     */
    @SerializedName("initialization_required")
    INITIALIZATION_REQUIRED,

    /**
     * The payment method details are invalid. The flags describe what is missing or wrong.
     */
    @SerializedName("invalid")
    INVALID

}
