package com.checkout.payments.request.source.apm;

import com.google.gson.annotations.SerializedName;

/**
 * The type of account holder on a SEPA payment source.
 *
 * <p>This position declares two values only, unlike {@link com.checkout.common.AccountHolderType}
 * which also declares "government". Declared here rather than reusing
 * {@link com.checkout.instruments.InstrumentAccountHolderType} so the payments source packages do
 * not depend on the instruments packages.
 *
 * <p>The values serialize lowercase. The specification declares them capitalized at this one
 * position, but every other account-holder-type position declares them lowercase and every other
 * Checkout.com SDK sends lowercase. Pending confirmation from the API owners.
 */
public enum RequestSepaAccountHolderType {

    @SerializedName("individual")
    INDIVIDUAL,

    @SerializedName("corporate")
    CORPORATE

}
