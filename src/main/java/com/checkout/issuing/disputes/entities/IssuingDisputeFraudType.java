package com.checkout.issuing.disputes.entities;

import com.google.gson.annotations.SerializedName;

/**
 * The type of fraud the cardholder is asserting.
 */
public enum IssuingDisputeFraudType {

    @SerializedName("card_lost")
    CARD_LOST,

    @SerializedName("card_stolen")
    CARD_STOLEN,

    @SerializedName("card_never_received")
    CARD_NEVER_RECEIVED,

    @SerializedName("fraudulent_account")
    FRAUDULENT_ACCOUNT,

    @SerializedName("counterfeit_card")
    COUNTERFEIT_CARD,

    @SerializedName("account_takeover")
    ACCOUNT_TAKEOVER,

    @SerializedName("card_not_present_fraud")
    CARD_NOT_PRESENT_FRAUD,

    @SerializedName("merchant_misrepresentation")
    MERCHANT_MISREPRESENTATION,

    @SerializedName("cardholder_manipulation")
    CARDHOLDER_MANIPULATION,

    @SerializedName("incorrect_processing")
    INCORRECT_PROCESSING,

    @SerializedName("other")
    OTHER
}
