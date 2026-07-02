package com.checkout.accounts.reserverules.entities;

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

    private HoldingDuration holdingDuration;

}