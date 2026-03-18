package com.checkout.issuing.transactions.entities;

import com.google.gson.annotations.SerializedName;

public enum MessageIndicator {
    @SerializedName("incremental_preauthorization")
    INCREMENTAL_PREAUTHORIZATION,

    @SerializedName("deferred_authorization")
    DEFERRED_AUTHORIZATION,

    @SerializedName("preauthorization")
    PREAUTHORIZATION,

    @SerializedName("normal_authorization")
    NORMAL_AUTHORIZATION,

    @SerializedName("final_authorization")
    FINAL_AUTHORIZATION,

    @SerializedName("partial_reversal")
    PARTIAL_REVERSAL,

    @SerializedName("full_reversal")
    FULL_REVERSAL,

    @SerializedName("partial_presentment")
    PARTIAL_PRESENTMENT,

    @SerializedName("final_presentment")
    FINAL_PRESENTMENT,

    @SerializedName("unknown")
    UNKNOWN
}