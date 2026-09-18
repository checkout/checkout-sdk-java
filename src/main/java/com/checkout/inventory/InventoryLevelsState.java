package com.checkout.inventory;

import com.google.gson.annotations.SerializedName;

/**
 * A derived availability state for the variant.
 */
public enum InventoryLevelsState {

    @SerializedName("in_stock")
    IN_STOCK,

    @SerializedName("limited")
    LIMITED,

    @SerializedName("out_of_stock")
    OUT_OF_STOCK,

}
