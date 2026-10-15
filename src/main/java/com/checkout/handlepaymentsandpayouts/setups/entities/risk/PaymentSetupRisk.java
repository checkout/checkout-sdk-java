package com.checkout.handlepaymentsandpayouts.setups.entities.risk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Risk assessment settings for the Payment Setup.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentSetupRisk {

    /**
     * Indicates whether a risk assessment should be performed.
     * [Optional]
     * Default: true
     */
    private Boolean enabled;
}
