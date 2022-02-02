package com.checkout.disputes;

import com.google.gson.annotations.SerializedName;

public enum DisputeCategory {

    @SerializedName("general")
    GENERAL,

    @SerializedName("duplicate")
    DUPLICATE,

    @SerializedName("fraudulent")
    FRAUDULENT,

    @SerializedName("unrecognized")
    UNRECOGNIZED,

    @SerializedName("incorrect_amount")
    INCORRECT_AMOUNT,

    @SerializedName("not_as_described")
    NOT_AS_DESCRIBED,

    @SerializedName("credit_not_issued")
    CREDIT_NOT_ISSUED,

    @SerializedName("canceled_recurring")
    CANCELED_RECURRING,

    @SerializedName("product_service_not_received")
    PRODUCT_SERVICE_NOT_RECEIVED

}
