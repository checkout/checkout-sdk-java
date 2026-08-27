package com.checkout.accounts.payout.schedule.request;

import com.checkout.accounts.payout.schedule.ScheduleFrequency;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
/**
 * A daily payout schedule.
 *
 * <p>For SaaS seller (ISV) sub-entities this runs on working days only, Monday to Friday, with no
 * payout at weekends, and is based on the available balance as of 00:00 in the sub-entity's time
 * zone. Standard sub-entities are paid out every day.
 */
public final class ScheduleFrequencyDailyRequest extends ScheduleRequest {

    public ScheduleFrequencyDailyRequest() {
        super(ScheduleFrequency.DAILY);
    }
}
