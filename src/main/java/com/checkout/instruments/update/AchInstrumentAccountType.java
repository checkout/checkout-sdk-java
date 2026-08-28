package com.checkout.instruments.update;

import com.google.gson.annotations.SerializedName;

/**
 * The type of Direct Debit account of an ACH instrument.
 *
 * <p>Shared by the store, update and retrieve ACH instrument variants, which all declare the same
 * two values. The package is historical: the enum was introduced with the update request and is now
 * used by the create and get variants as well.
 */
public enum AchInstrumentAccountType {

    @SerializedName("savings")
    SAVINGS,

    @SerializedName("checking")
    CHECKING

}
