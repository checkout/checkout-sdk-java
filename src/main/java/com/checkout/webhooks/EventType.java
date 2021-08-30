package com.checkout.webhooks;

import com.google.gson.annotations.SerializedName;

public enum EventType {

    @SerializedName("payment_approved")
    PAYMENT_APPROVED("payment_approved"),
    @SerializedName("payment_pending")
    PAYMENT_PENDING("payment_pending"),
    @SerializedName("payment_declined")
    PAYMENT_DECLINED("payment_declined"),
    @SerializedName("payment_expired")
    PAYMENT_EXPIRED("payment_expired"),
    @SerializedName("payment_canceled")
    PAYMENT_CANCELED("payment_canceled"),
    @SerializedName("payment_voided")
    PAYMENT_VOIDED("payment_voided"),
    @SerializedName("payment_void_declined")
    PAYMENT_VOID_DECLINED("payment_void_declined"),
    @SerializedName("payment_captured")
    PAYMENT_CAPTURED("payment_captured"),
    @SerializedName("payment_capture_declined")
    PAYMENT_CAPTURE_DECLINED("payment_capture_declined"),
    @SerializedName("payment_capture_pending")
    PAYMENT_CAPTURE_PENDING("payment_capture_pending"),
    @SerializedName("payment_refunded")
    PAYMENT_REFUNDED("payment_refunded"),
    @SerializedName("payment_refund_declined")
    PAYMENT_REFUNDED_DECLINED("payment_refund_declined"),
    @SerializedName("payment_refund_pending")
    PAYMENT_REFUND_PENDING("payment_refund_pending");

    private final String code;

    EventType(final String code) {
        this.code = code;
    }

    /**
     * Will be removed in a future version.
     */
    @Deprecated
    public String getCode() {
        return code;
    }

}
