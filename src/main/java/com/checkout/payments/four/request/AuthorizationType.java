package com.checkout.payments.four.request;

import com.google.gson.annotations.SerializedName;

public enum AuthorizationType {

    @SerializedName("Final")
    FINAL,
    @SerializedName("Estimated")
    ESTIMATED,
    @SerializedName("Incremental")
    INCREMENTAL

}