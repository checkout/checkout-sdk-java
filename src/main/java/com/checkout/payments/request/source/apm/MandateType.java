package com.checkout.payments.request.source.apm;

import com.google.gson.annotations.SerializedName;

/**
 * The type of SEPA mandate.
 *
 * <p>The wire values are capitalized, and the specification allows these two values only.
 */
public enum MandateType {

    @SerializedName("Core")
    CORE,

    @SerializedName("B2B")
    B2B,

}
