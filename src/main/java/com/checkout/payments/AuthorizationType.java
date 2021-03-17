package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum AuthorizationType {
    @SerializedName("Final")
    FINAL,
    @SerializedName("Estimated")
    ESTIMATED
}