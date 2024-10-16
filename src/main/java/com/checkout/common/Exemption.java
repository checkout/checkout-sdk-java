package com.checkout.common;

import com.google.gson.annotations.SerializedName;

public enum Exemption {

    @SerializedName("low_value")
    LOW_VALUE,
    @SerializedName("trusted_listing")
    TRUSTED_LISTING,
    @SerializedName("trusted_listing_prompt")
    TRUSTED_LISTING_PROMPT,
    @SerializedName("transaction_risk_assessment")
    TRANSACTION_RISK_ASSESSMENT,
    @SerializedName("3ds_outage")
    THREE_DS_OUTAGE,
    @SerializedName("sca_delegation")
    SCA_DELEGATION,
    @SerializedName("out_of_sca_scope")
    OUT_OF_SCA_SCOPE,
    @SerializedName("low_risk_program")
    LOW_RISK_PROGRAM,
    @SerializedName("recurring_operation")
    RECURRING_OPERATION,
    @SerializedName("data_share")
    DATA_SHARE,
    @SerializedName("other")
    OTHER,
    @SerializedName(value = "None", alternate = {"NONE", "none"})
    NONE,
    @SerializedName("secure_corporate_payment")
    SECURE_CORPORATE_PAYMENT,

}
