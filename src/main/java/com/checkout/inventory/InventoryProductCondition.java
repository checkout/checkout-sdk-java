package com.checkout.inventory;

import com.google.gson.annotations.SerializedName;

/**
 * The product's condition. Always present on the response; defaults to {@code new} when not
 * provided on the request.
 */
public enum InventoryProductCondition {

    @SerializedName("new")
    NEW,

    @SerializedName("used")
    USED,

    @SerializedName("refurbished")
    REFURBISHED,

}
