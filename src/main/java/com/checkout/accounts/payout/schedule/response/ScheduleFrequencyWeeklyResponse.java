package com.checkout.accounts.payout.schedule.response;

import com.checkout.accounts.payout.schedule.DaySchedule;
import com.checkout.accounts.payout.schedule.ScheduleFrequency;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class ScheduleFrequencyWeeklyResponse extends ScheduleResponse {

    private List<DaySchedule> byDay;

    public ScheduleFrequencyWeeklyResponse() {
        super(ScheduleFrequency.WEEKLY);
    }
}
