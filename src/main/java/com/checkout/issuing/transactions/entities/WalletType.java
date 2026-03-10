package com.checkout.issuing.transactions.entities;

import com.google.gson.annotations.SerializedName;

public enum WalletType {
    @SerializedName("googlepay")
    GOOGLEPAY,
    
    @SerializedName("applepay")
    APPLEPAY,

    @SerializedName("remote_commerce_programs")
    REMOTE_COMMERCE_PROGRAMS;
}