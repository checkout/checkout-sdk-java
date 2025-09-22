package com.checkout.handlepaymentsandpayouts.payments.common.source.cardsource;

import com.google.gson.annotations.SerializedName;

public enum CardType {

    @SerializedName("CREDIT")
    CREDIT,

    @SerializedName("DEBIT")
    DEBIT,

    @SerializedName("PREPAID")
    PREPAID,

    @SerializedName("CHARGE")
    CHARGE,

    @SerializedName("DEFERRED DEBIT")
    DEFERRED_DEBIT,

}
