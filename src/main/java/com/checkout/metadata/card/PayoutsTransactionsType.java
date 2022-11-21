package com.checkout.metadata.card;

import com.google.gson.annotations.SerializedName;

public enum PayoutsTransactionsType {

    @SerializedName("not_supported")
    NOT_SUPPORTED,
    @SerializedName("standard")
    STANDARD,
    @SerializedName("fast_founds")
    FAST_FOUNDS,
    @SerializedName("unknown")
    UNKNOWN
}
