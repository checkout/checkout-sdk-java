package com.checkout.payments.beta.request;

import com.google.gson.annotations.SerializedName;

public enum AuthorizationType {

    @SerializedName("Final")
    FINAL,
    @SerializedName("Estimated")
    ESTIMATED,
    @SerializedName("Incremental")
    INCREMENTAL

}