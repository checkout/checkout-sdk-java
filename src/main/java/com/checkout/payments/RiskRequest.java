package com.checkout.payments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class RiskRequest {

    private boolean enabled;

}

