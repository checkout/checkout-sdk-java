package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum AccountTypeCardProduct {

    @SerializedName("credit")
    CREDIT,
    @SerializedName("debit")
    DEBIT,
    @SerializedName("not_applicable")
    NOT_APPLICABLE

}
