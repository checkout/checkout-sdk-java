package com.checkout.accounts.payout.schedule.response;

import com.checkout.accounts.payout.schedule.DaySchedule;
import com.checkout.accounts.payout.schedule.ScheduleFrequency;
import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class ScheduleFrequencyWeeklyResponse extends ScheduleResponse {

    @SerializedName("by_day")
    private DaySchedule byDay;

    public ScheduleFrequencyWeeklyResponse() {
        super(ScheduleFrequency.WEEKLY);
    }
}
