package com.checkout.marketplace.transfers;

import com.google.gson.annotations.SerializedName;

public enum TransferStatus {

    @SerializedName("pending")
    PENDING,
    @SerializedName("completed")
    COMPLETED,
    @SerializedName("rejected")
    REJECTED
}
