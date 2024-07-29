package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum CardholderAccountAgeIndicator {

    @SerializedName("less_than_thirty_days")
    LESS_THAN_THIRTY_DAYS,
    @SerializedName("more_than_sixty_days")
    MORE_THAN_SIXTY_DAYS,
    @SerializedName("no_account")
    NO_ACCOUNT,
    @SerializedName("thirty_to_sixty_days")
    THIRTY_TO_SIXTY_DAYS,
    @SerializedName("this_transaction")
    THIS_TRANSACTION

}
