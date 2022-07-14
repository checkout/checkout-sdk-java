package com.checkout.sources.previous;

import com.google.gson.annotations.SerializedName;

public enum SourceType {

    @SerializedName(value = "sepa", alternate = {"Sepa"})
    SEPA
}
