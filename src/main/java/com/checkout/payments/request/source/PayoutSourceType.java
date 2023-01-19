package com.checkout.payments.request.source;

import com.google.gson.annotations.SerializedName;

public enum PayoutSourceType {

    @SerializedName("currency_account")
    CURRENCY_ACCOUNT,
    @SerializedName("entity")
    ENTITY

}
