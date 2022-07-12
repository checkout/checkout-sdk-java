package com.checkout.accounts.payout.schedule.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class UpdateScheduleRequest {

    private boolean enabled;

    private int threshold;

    private ScheduleRequest recurrence;

}
