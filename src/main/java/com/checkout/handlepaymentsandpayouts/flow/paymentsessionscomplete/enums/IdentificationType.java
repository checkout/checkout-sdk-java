package com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.enums;

import com.google.gson.annotations.SerializedName;

/** The type of identification used to identify the sender */
public enum IdentificationType {
    @SerializedName("passport")
    PASSPORT,
    @SerializedName("driving_licence")
    DRIVING_LICENCE,
    @SerializedName("national_id")
    NATIONAL_ID
}
