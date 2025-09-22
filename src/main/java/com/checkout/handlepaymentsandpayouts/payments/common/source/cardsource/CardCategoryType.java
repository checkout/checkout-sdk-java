package com.checkout.handlepaymentsandpayouts.payments.common.source.cardsource;

import com.google.gson.annotations.SerializedName;

public enum CardCategoryType {

    @SerializedName("CONSUMER")
    CONSUMER,

    @SerializedName("COMMERCIAL")
    COMMERCIAL,

}
