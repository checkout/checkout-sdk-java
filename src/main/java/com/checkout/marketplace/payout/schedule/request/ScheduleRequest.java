package com.checkout.marketplace.payout.schedule.request;

import com.checkout.marketplace.payout.schedule.ScheduleFrequency;
import lombok.Data;

@Data
public abstract class ScheduleRequest {

    private ScheduleFrequency frequency;

    public ScheduleRequest(final ScheduleFrequency frequency) {
        this.frequency = frequency;
    }
}
