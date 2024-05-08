package com.checkout.common;

import com.google.gson.annotations.SerializedName;

public enum CardWalletType {

    @SerializedName("applepay")
    APPLEPAY,
    @SerializedName("googlepay")
    GOOGLEPAY,

}
