package com.checkout.handlepaymentsandpayouts.setups.entities.accountFundingTransaction;

import com.google.gson.annotations.SerializedName;

public enum AccountFundingTransactionIdentificationType {

    @SerializedName("passport")
    PASSPORT,

    @SerializedName("driving_license")
    DRIVING_LICENSE,

    @SerializedName("national_id")
    NATIONAL_ID
}
