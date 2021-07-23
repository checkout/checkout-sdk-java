package com.checkout.tokens.beta;

import com.google.gson.annotations.SerializedName;

public enum WalletType {

    @SerializedName("applepay")
    APPLE_PAY,

    @SerializedName("googlepay")
    GOOGLE_PAY

}