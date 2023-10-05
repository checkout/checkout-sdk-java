package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum AccountChangeIndicator {
    @SerializedName("this_transaction")
    THIS_TRANSACTION,

    @SerializedName("less_than_thirty_days")
    LESS_THAN_THIRTY_DAYS,

    @SerializedName("thirty_to_sixty_days")
    THIRTY_TO_SIXTY_DAYS,

    @SerializedName("more_than_sixty_days")
    MORE_THAN_SIXTY_DAYS,
}
