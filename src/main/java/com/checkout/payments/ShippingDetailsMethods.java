package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum ShippingDetailsMethods {
    
    @SerializedName("Digital")
    DIGITAL,
    
    @SerializedName("PickUp")
    PICK_UP,
    
    @SerializedName("BillingAddress")
    BILLING_ADDRESS,
    
    @SerializedName("OtherAddress")
    OTHER_ADDRESS,
}
