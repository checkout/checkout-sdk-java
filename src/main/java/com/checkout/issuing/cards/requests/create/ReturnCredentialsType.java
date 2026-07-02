package com.checkout.issuing.cards.requests.create;

import com.google.gson.annotations.SerializedName;

/**
 * The credentials to retrieve on card creation.
 */
public enum ReturnCredentialsType {

    @SerializedName("number")
    NUMBER,

    @SerializedName("cvc2")
    CVC2
}
