package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum AccountUpdateStatusType {

    @SerializedName("card_updated")
    CARD_UPDATED,

    @SerializedName("card_expiry_updated")
    CARD_EXPIRY_UPDATED,

    @SerializedName("card_closed")
    CARD_CLOSED,

    @SerializedName("contact_cardholder")
    CONTACT_CARDHOLDER

}
