package com.checkout.accounts.payout.schedule.request;

import com.checkout.accounts.payout.schedule.DaySchedule;
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
public final class ScheduleFrequencyWeeklyRequest extends ScheduleRequest {

    @SerializedName("by_day")
    private List<DaySchedule> byDays;

    @Builder
    private ScheduleFrequencyWeeklyRequest(final List<DaySchedule> byDays) {
        super(ScheduleFrequency.WEEKLY);
        this.byDays = byDays;
    }

    public ScheduleFrequencyWeeklyRequest() {
        super(ScheduleFrequency.WEEKLY);
    }
}
