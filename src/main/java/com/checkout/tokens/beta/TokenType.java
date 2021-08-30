package com.checkout.tokens.beta;

import com.google.gson.annotations.SerializedName;

public enum TokenType {

    @SerializedName("card")
    CARD,
    @SerializedName("applepay")
    APPLEPAY,
    @SerializedName("googlepay")
    GOOGLEPAY

}
