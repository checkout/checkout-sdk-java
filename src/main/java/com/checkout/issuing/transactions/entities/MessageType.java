package com.checkout.issuing.transactions.entities;

import com.google.gson.annotations.SerializedName;

public enum MessageType {
    @SerializedName("authorization")
    AUTHORIZATION,

    @SerializedName("reversal")
    REVERSAL,

    @SerializedName("authorization_advice")
    AUTHORIZATION_ADVICE,

    @SerializedName("reversal_advice")
    REVERSAL_ADVICE,

    @SerializedName("presentment")
    PRESENTMENT,

    @SerializedName("authorization_refund")
    AUTHORIZATION_REFUND,

    @SerializedName("presentment_refund")
    PRESENTMENT_REFUND,

    @SerializedName("presentment_reversed")
    PRESENTMENT_REVERSED,

    @SerializedName("presentment_reversed_refund")
    PRESENTMENT_REVERSED_REFUND
}