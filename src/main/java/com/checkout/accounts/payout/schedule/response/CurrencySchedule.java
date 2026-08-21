package com.checkout.accounts.payout.schedule.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public final class CurrencySchedule {

    private Boolean enabled;

    private Integer threshold;

    /**
     * The ID of the platforms payment instrument this schedule pays out to.
     */
    @SerializedName("payment_instrument_id")
    private String paymentInstrumentId;

    /**
     * The amount, in the minor units of the schedule's currency, retained in the sub-entity's
     * available balance. Only the funds above this are paid out.
     *
     * <p>Returned for SaaS seller (ISV) schedules.
     */
    @SerializedName("balance_minimum")
    private Long balanceMinimum;

    /**
     * Whether a balance below the configured minimum is carried forward to the next payout.
     * Always returned for SaaS sellers, where it defaults to {@code false}.
     */
    @SerializedName("carry_forward_enabled")
    private Boolean carryForwardEnabled;

    private ScheduleResponse recurrence;

}
