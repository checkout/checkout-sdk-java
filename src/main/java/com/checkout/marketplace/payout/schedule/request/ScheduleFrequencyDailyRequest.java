package com.checkout.marketplace.payout.schedule.request;

import com.checkout.marketplace.payout.schedule.ScheduleFrequency;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class ScheduleFrequencyDailyRequest extends ScheduleRequest {

    public ScheduleFrequencyDailyRequest() {
        super(ScheduleFrequency.DAILY);
    }
}
