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
