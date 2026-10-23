package com.checkout.issuing.cards.requests.suspend;

import com.google.gson.annotations.SerializedName;

/**
 * The reason why the card is being suspended.
 */
public enum SuspendReason {

    @SerializedName("suspected_lost")
    SUSPECTED_LOST,
    @SerializedName("suspected_stolen")
    SUSPECTED_STOLEN

}
