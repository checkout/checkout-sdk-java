package com.checkout.common;

import com.google.gson.annotations.SerializedName;

public enum NameCheckType {

    @SerializedName("full_match")
    FULL_MATCH,
    @SerializedName("partial_match")
    PARTIAL_MATCH,
    @SerializedName("no_match")
    NO_MATCH,
}
