package com.checkout.common;

import com.google.gson.annotations.SerializedName;

public enum CardCategory {

    @SerializedName(value = "Consumer", alternate = {"CONSUMER", "consumer"})
    CONSUMER,

    @SerializedName(value = "Commercial", alternate = {"COMMERCIAL", "commercial"})
    COMMERCIAL,

    @SerializedName(value = "All", alternate = {"ALL", "all"})
    ALL,

    @SerializedName(value = "Other", alternate = {"OTHER", "other"})
    OTHER,

    @SerializedName(value = "NotSet", alternate = {"NOTSET", "NOT_SET", "Not_Set", "notset", "not_set"})
    NOT_SET

}
