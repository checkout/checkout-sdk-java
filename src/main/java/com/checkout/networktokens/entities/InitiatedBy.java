package com.checkout.networktokens.entities;

import com.google.gson.annotations.SerializedName;

public enum InitiatedBy {
    @SerializedName("cardholder")
    CARDHOLDER,
    
    @SerializedName("token_requestor")
    TOKEN_REQUESTOR
}