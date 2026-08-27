package com.checkout.apm.bacs;

import com.google.gson.annotations.SerializedName;

/**
 * The type of pre-notification being sent to the payer.
 */
public enum BacsNotificationType {

    @SerializedName("advance_notice")
    ADVANCE_NOTICE

}
