package com.checkout.instruments;

import com.google.gson.annotations.SerializedName;

/**
 * The type of account holder of a stored payment instrument.
 *
 * <p>Shared by the Bacs Direct Debit, SEPA and ACH instrument variants, which all declare the same
 * two values. This is deliberately not {@link com.checkout.common.AccountHolderType}, which also
 * declares {@code government} and is therefore wider than the instrument schemas allow.
 */
public enum InstrumentAccountHolderType {

    @SerializedName("individual")
    INDIVIDUAL,

    @SerializedName("corporate")
    CORPORATE

}
