package com.checkout.issuing.transactions.entities;

import com.google.gson.annotations.SerializedName;

public enum MessageResult {
    @SerializedName("approved")
    APPROVED,

    @SerializedName("declined")
    DECLINED,

    @SerializedName("processed")
    PROCESSED
}