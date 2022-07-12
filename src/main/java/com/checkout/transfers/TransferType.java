package com.checkout.transfers;

import com.google.gson.annotations.SerializedName;

public enum TransferType {

    @SerializedName("commission")
    COMMISSION,
    @SerializedName("promotion")
    PROMOTION,
    @SerializedName("refund")
    REFUND,

}
