package com.checkout.accounts.payout.schedule.response;

import com.checkout.accounts.payout.schedule.ScheduleFrequency;
import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class ScheduleFrequencyMonthlyResponse extends ScheduleResponse {

    @SerializedName("by_month_day")
    private List<Integer> byMonthDay;

    public ScheduleFrequencyMonthlyResponse() {
        super(ScheduleFrequency.MONTHLY);
    }
}
