package com.checkout.payments.beta.request;

import com.google.gson.annotations.SerializedName;

public enum Exemption {

    @SerializedName("low_value")
    LOW_VALUE,
    @SerializedName("secure_corporate_payment")
    SECURE_CORPORATE_PAYMENT,
    @SerializedName("trusted_listing")
    TRUSTED_LISTING

}
