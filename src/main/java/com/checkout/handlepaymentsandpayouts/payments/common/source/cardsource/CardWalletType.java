package com.checkout.handlepaymentsandpayouts.payments.common.source.cardsource;

import com.google.gson.annotations.SerializedName;

public enum CardWalletType {

    @SerializedName("applepay")
    APPLEPAY,

    @SerializedName("googlepay")
    GOOGLEPAY,

}
