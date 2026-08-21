package com.checkout.accounts.payout.schedule.request;

import com.checkout.accounts.payout.schedule.DaySchedule;
import com.checkout.accounts.payout.schedule.ScheduleFrequency;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
/**
 * A weekly payout schedule.
 *
 * <p>SaaS seller (ISV) sub-entities accept working days only, Monday to Friday: a schedule set to
 * a Saturday or Sunday is rejected. Their payout is based on the available balance as of 00:00 in
 * the sub-entity's time zone. Standard sub-entities accept any day.
 */
public final class ScheduleFrequencyWeeklyRequest extends ScheduleRequest {

    @SerializedName("by_day")
    private List<DaySchedule> byDays;

    @Builder
    private ScheduleFrequencyWeeklyRequest(final List<DaySchedule> byDays) {
        super(ScheduleFrequency.WEEKLY);
        this.byDays = byDays;
    }

    public ScheduleFrequencyWeeklyRequest() {
        super(ScheduleFrequency.WEEKLY);
    }
}
