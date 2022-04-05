package com.checkout.common;

import com.google.gson.annotations.SerializedName;

public enum Exemption {

    @SerializedName("None")
    NONE,
    @SerializedName("low_value")
    LOW_VALUE,
    @SerializedName("recurring_operation")
    RECURRING_OPERATION,
    @SerializedName("transaction_risk_assessment")
    TRANSACTION_RISK_ASSESSMENT,
    @SerializedName("secure_corporate_payment")
    SECURE_CORPORATE_PAYMENT,
    @SerializedName("trusted_listing")
    TRUSTED_LISTING,
    @SerializedName("3ds_outage")
    THREE_DS_OUTAGE,
    @SerializedName("sca_delegation")
    SCA_DELEGATION,
    @SerializedName("out_of_sca_scope")
    OUT_OF_SCA_SCOPE,
    @SerializedName("other")
    OTHER,
    @SerializedName("low_risk_program")
    LOW_RISK_PROGRAM

}
