package com.checkout.inventory;

import com.google.gson.annotations.SerializedName;

/**
 * The current state of a reservation hold. A {@code held} reservation past its
 * {@code expires_at} is reported as {@code expired}. Transitions out of {@code held} are terminal.
 */
public enum InventoryReservationState {

    @SerializedName("held")
    HELD,

    @SerializedName("committed")
    COMMITTED,

    @SerializedName("released")
    RELEASED,

    @SerializedName("expired")
    EXPIRED,

}
