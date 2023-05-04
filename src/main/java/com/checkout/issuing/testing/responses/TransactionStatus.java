package com.checkout.issuing.testing.responses;

import com.google.gson.annotations.SerializedName;

public enum TransactionStatus {

    @SerializedName("Authorized")
    AUTHORIZED,
    @SerializedName("Declined")
    DECLINED

}
