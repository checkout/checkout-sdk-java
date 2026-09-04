package com.checkout.payments.request.source.apm;

import com.google.gson.annotations.SerializedName;

/**
 * The type of Direct Debit account on an ACH payment source.
 *
 * <p>PaymentRequestAchSource is the only schema declaring this set of values. Two neighbouring
 * enums are deliberately different and are not interchangeable:
 *
 * <ul>
 *   <li>{@link com.checkout.common.AccountType} is savings / current / cash and serves the
 *       bank-account instrument and destination positions, so it cannot express "checking".
 *   <li>{@link com.checkout.instruments.update.AchInstrumentAccountType} is savings / checking and
 *       serves the stored ACH instrument positions, so it does not declare "cash".
 * </ul>
 */
public enum AchSourceAccountType {

    @SerializedName("savings")
    SAVINGS,

    @SerializedName("checking")
    CHECKING,

    @SerializedName("cash")
    CASH

}
