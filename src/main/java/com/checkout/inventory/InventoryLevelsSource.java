package com.checkout.inventory;

import com.google.gson.annotations.SerializedName;

/**
 * How the levels are maintained. Always {@code managed} in the current version.
 */
public enum InventoryLevelsSource {

    @SerializedName("managed")
    MANAGED,

    @SerializedName("sync")
    SYNC,

}
