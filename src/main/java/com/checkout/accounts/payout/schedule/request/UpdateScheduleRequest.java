package com.checkout.accounts.payout.schedule.request;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class UpdateScheduleRequest {

    private boolean enabled;

    private int threshold;

    /**
     * The amount, in the minor units of the schedule's currency, to retain in the sub-entity's
     * available balance. Checkout.com pays out only the funds above this, and generates no payout
     * if there are none. Defaults to {@code 0} when not set.
     *
     * <p>SaaS seller (ISV) schedules only.
     */
    @SerializedName("balance_minimum")
    private Long balanceMinimum;

    /**
     * Whether to carry forward to the next payout any balance below the configured minimum.
     * Defaults to {@code false} when not set.
     *
     * <p>SaaS seller (ISV) schedules only.
     */
    @SerializedName("carry_forward_enabled")
    private Boolean carryForwardEnabled;

    private ScheduleRequest recurrence;

}
