package com.checkout.issuing.transactions.entities;

import com.google.gson.annotations.SerializedName;

public enum ReferenceType {
    @SerializedName("original_mit")
    ORIGINAL_MIT,

    @SerializedName("original_recurring")
    ORIGINAL_RECURRING;
}