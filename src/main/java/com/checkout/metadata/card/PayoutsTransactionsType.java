package com.checkout.metadata.card;

import com.google.gson.annotations.SerializedName;

public enum PayoutsTransactionsType {

    @SerializedName("not_supported")
    NOT_SUPPORTED,
    @SerializedName("standard")
    STANDARD,
    @SerializedName("fast_funds")
    FAST_FUNDS,
    @SerializedName("unknown")
    UNKNOWN
}
