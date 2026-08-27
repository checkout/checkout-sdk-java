package com.checkout.accounts.payout.schedule.request;

import com.checkout.accounts.payout.schedule.ScheduleFrequency;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
/**
 * A monthly payout schedule.
 *
 * <p>SaaS seller (ISV) sub-entities accept only these combinations, in any order: {@code [1]},
 * {@code [15]}, {@code [1, 15]} or {@code [1, 16]}. Their payout is based on the available balance
 * as of 00:00 in the sub-entity's time zone. Standard sub-entities accept any day from 1 to 28.
 */
public final class ScheduleFrequencyMonthlyRequest extends ScheduleRequest {

    @SerializedName("by_month_day")
    private List<Integer> byMonthDays;

    @Builder
    private ScheduleFrequencyMonthlyRequest(final List<Integer> byMonthDays) {
        super(ScheduleFrequency.MONTHLY);
        this.byMonthDays = byMonthDays;
    }

    public ScheduleFrequencyMonthlyRequest() {
        super(ScheduleFrequency.MONTHLY);
    }
}
