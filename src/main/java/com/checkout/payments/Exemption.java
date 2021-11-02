package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum Exemption {

    @SerializedName("low_value")
    LOW_VALUE,
    @SerializedName("secure_corporate_payment")
    SECURE_CORPORATE_PAYMENT,
    @SerializedName("trusted_listing")
    TRUSTED_LISTING,
    @SerializedName("transaction_risk_assessment")
    TRANSACTION_RISK_ASSESSMENT

}
