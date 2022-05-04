package com.checkout.sources;

import com.google.gson.annotations.SerializedName;

public enum SourceType {

    @SerializedName(value = "sepa", alternate = {"Sepa"})
    SEPA
}
