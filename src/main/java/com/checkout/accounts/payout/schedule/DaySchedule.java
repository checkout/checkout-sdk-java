package com.checkout.accounts.payout.schedule;

import com.google.gson.annotations.SerializedName;

public enum DaySchedule {
    @SerializedName(value = "monday", alternate = {"Monday"})
    MONDAY,
    @SerializedName(value = "tuesday", alternate = {"Tuesday"})
    TUESDAY,
    @SerializedName(value = "wednesday", alternate = {"Wednesday"})
    WEDNESDAY,
    @SerializedName(value = "thursday", alternate = {"Thursday"})
    THURSDAY,
    @SerializedName(value = "friday", alternate = {"Friday"})
    FRIDAY,
    @SerializedName(value = "saturday", alternate = {"Saturday"})
    SATURDAY,
    @SerializedName(value = "sunday", alternate = {"Sunday"})
    SUNDAY
}
