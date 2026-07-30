package com.checkout.common;

import com.google.gson.annotations.SerializedName;

public enum CardType {

    @SerializedName(value = "Credit", alternate = {"CREDIT", "credit"})
    CREDIT,
    @SerializedName(value = "Debit", alternate = {"DEBIT", "debit"})
    DEBIT,
    @SerializedName(value = "Prepaid", alternate = {"PREPAID", "prepaid"})
    PREPAID,
    @SerializedName(value = "Charge", alternate = {"CHARGE", "charge"})
    CHARGE,
    @SerializedName(value = "Deferred Debit", alternate = {"DEFERRED DEBIT", "deferred debit"})
    DEFERRED_DEBIT,

    // NetworkToken is returned only by the card metadata endpoint.
    @SerializedName(value = "Network Token", alternate = {"NETWORK TOKEN", "network token"})
    NETWORK_TOKEN,
    // Unknown is returned only on card payout destinations.
    @SerializedName(value = "Unknown", alternate = {"UNKNOWN", "unknown"})
    UNKNOWN

}
