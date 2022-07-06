package com.checkout.payments.four.sender;

import com.google.gson.annotations.SerializedName;

public enum SourceOfFunds {

    @SerializedName("credit")
    CREDIT,
    @SerializedName("debit")
    DEBIT,
    @SerializedName("prepaid")
    PREPAID,
    @SerializedName("deposit_account")
    DEPOSIT_ACCOUNT,
    @SerializedName("mobile_money_account")
    MOBILE_MONEY_ACCOUNT,
    @SerializedName("cash")
    CASH
}
