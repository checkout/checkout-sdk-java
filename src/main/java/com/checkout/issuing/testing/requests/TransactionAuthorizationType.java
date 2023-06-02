package com.checkout.issuing.testing.requests;

import com.google.gson.annotations.SerializedName;

public enum TransactionAuthorizationType {

    @SerializedName("authorization")
    AUTHORIZATION,

    @SerializedName("pre_authorization")
    PRE_AUTHORIZATION
}
