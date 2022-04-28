package com.checkout.marketplace.payout.schedule;

import com.google.gson.annotations.SerializedName;

public enum ScheduleFrequency {

    @SerializedName(value = "Weekly")
    WEEKLY,
    @SerializedName(value = "Daily")
    DAILY,
    @SerializedName(value = "Monthly")
    MONTHLY
}
