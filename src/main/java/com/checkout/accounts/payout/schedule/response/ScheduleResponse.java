package com.checkout.accounts.payout.schedule.response;

import com.checkout.accounts.payout.schedule.ScheduleFrequency;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public abstract class ScheduleResponse {

    private final ScheduleFrequency frequency;

    protected ScheduleResponse(final ScheduleFrequency frequency) {
        this.frequency = frequency;
    }
}
