package com.checkout.payments.four;

import com.google.gson.annotations.SerializedName;

public enum AuthorizationType {

    @SerializedName("Final")
    FINAL,
    @SerializedName("Estimated")
    ESTIMATED,
    @SerializedName("Incremental")
    INCREMENTAL

}