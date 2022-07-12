package com.checkout.accounts.payout.schedule.request;

import com.checkout.accounts.payout.schedule.ScheduleFrequency;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class ScheduleFrequencyDailyRequest extends ScheduleRequest {

    public ScheduleFrequencyDailyRequest() {
        super(ScheduleFrequency.DAILY);
    }
}
