package com.checkout.marketplace.payout.schedule.request;

import com.checkout.marketplace.payout.schedule.ScheduleFrequency;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class ScheduleFrequencyMonthlyRequest extends ScheduleRequest {

    @SerializedName("by_month_day")
    private Integer byMonthDay;

    @Builder
    private ScheduleFrequencyMonthlyRequest(final Integer byMonthDay) {
        super(ScheduleFrequency.MONTHLY);
        this.byMonthDay = byMonthDay;
    }

    public ScheduleFrequencyMonthlyRequest() {
        super(ScheduleFrequency.MONTHLY);
    }
}
