package com.checkout.accounts.payout.schedule.request;

import com.checkout.accounts.payout.schedule.ScheduleFrequency;
import lombok.Data;

@Data
public abstract class ScheduleRequest {

    private ScheduleFrequency frequency;

    protected ScheduleRequest(final ScheduleFrequency frequency) {
        this.frequency = frequency;
    }
}
