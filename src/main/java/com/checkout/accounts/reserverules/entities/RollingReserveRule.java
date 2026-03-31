package com.checkout.accounts.reserverules.entities;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class RollingReserveRule {
    private double percentage;

    @SerializedName("holding_duration")
    private HoldingDuration holdingDuration;

}