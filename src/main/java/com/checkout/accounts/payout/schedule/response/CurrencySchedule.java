package com.checkout.accounts.payout.schedule.response;

import lombok.Data;

@Data
public final class CurrencySchedule {

    private Boolean enabled;

    private Integer threshold;

    private ScheduleResponse recurrence;

}
