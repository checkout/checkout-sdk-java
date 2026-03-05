package com.checkout.networktokens.entities;

import com.google.gson.annotations.SerializedName;

public enum DeletionReason {
    
    @SerializedName("fraud")
    FRAUD,

    @SerializedName("other")
    OTHER
}