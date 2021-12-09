package com.checkout.tokens;

import com.google.gson.annotations.SerializedName;

public enum TokenType {

    @SerializedName("card")
    CARD,
    @SerializedName("applepay")
    APPLEPAY,
    @SerializedName("googlepay")
    GOOGLEPAY

}
