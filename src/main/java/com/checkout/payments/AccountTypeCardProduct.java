package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum AccountTypeCardProduct {
    
    @SerializedName("not_applicable")
    NOT_APPLICABLE,

    @SerializedName("credit")
    CREDIT,

    @SerializedName("debit")
    DEBIT,
}
