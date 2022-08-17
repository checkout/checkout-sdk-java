package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum NetworkTokenType {

    @SerializedName("vts")
    VTS,
    @SerializedName("mdes")
    MDES,
    @SerializedName("applepay")
    APPLEPAY,
    @SerializedName("googlepay")
    GOOGLEPAY

}
