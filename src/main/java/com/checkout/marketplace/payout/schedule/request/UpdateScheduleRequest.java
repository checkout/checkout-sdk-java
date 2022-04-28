package com.checkout.marketplace.payout.schedule.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateScheduleRequest {

    private boolean enabled;

    private int threshold;

    private ScheduleRequest recurrence;

}
