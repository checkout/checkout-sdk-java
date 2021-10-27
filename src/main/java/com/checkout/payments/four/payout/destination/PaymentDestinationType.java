package com.checkout.payments.four.payout.destination;

import com.google.gson.annotations.SerializedName;

public enum PaymentDestinationType {

    @SerializedName("bank_account")
    BANK_ACCOUNT,

    @SerializedName("id")
    ID

}
