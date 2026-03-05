package com.checkout.networktokens.entities;

import com.google.gson.annotations.SerializedName;

public enum TransactionType {
    @SerializedName("ecom")  
    ECOM,

    @SerializedName("recurring")
    RECURRING,

    @SerializedName("pos")
    POS,

    @SerializedName("aft")
    AFT
}