package com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.enums;

import com.google.gson.annotations.SerializedName;

/** Indicates the reason for a merchant-initiated payment request. */
public enum MerchantInitiatedReasonType {
    @SerializedName("Delayed_charge")
    DELAYED_CHARGE,
    @SerializedName("Resubmission")
    RESUBMISSION,
    @SerializedName("No_show")
    NO_SHOW,
    @SerializedName("Reauthorization")
    REAUTHORIZATION
}
