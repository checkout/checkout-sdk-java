package com.checkout.issuing.transactions.entities;

import com.google.gson.annotations.SerializedName;

public enum TransactionStatus {
    @SerializedName("authorized")
    AUTHORIZED,

    @SerializedName("declined")
    DECLINED,

    @SerializedName("canceled")
    CANCELED,

    @SerializedName("cleared")
    CLEARED,

    @SerializedName("refunded")
    REFUNDED,
    
    @SerializedName("disputed")
    DISPUTED
}