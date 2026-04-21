package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class UpdatePaymentInstrumentRequest extends Headers {

    /**
     * A reference that you can use to identify the payment instrument.
     * [Optional]
     */
    private String label;

    /**
     * Whether the payment instrument should be set as the default payout destination.
     * For ad-hoc payouts the instrument is specified explicitly; for scheduled payouts the first instrument
     * created for a currency is used. To change the instrument for a payout schedule, update the schedule.
     * [Optional]
     * @deprecated Update the payout schedule instead.
     */
    @Deprecated
    @SerializedName("default")
    private Boolean defaultDestination;
}
