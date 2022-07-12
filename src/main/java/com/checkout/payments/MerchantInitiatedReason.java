package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum MerchantInitiatedReason {

    @SerializedName("Delayed_charge")
    DELAYED_CHARGE,
    @SerializedName("Resubmission")
    RESUBMISSION,
    @SerializedName("No_show")
    NO_SHOW,
    @SerializedName("Reauthorization")
    REAUTHORIZATION

}
