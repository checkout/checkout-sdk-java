package com.checkout.handlepaymentsandpayouts.flow.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Configuration options specific to Apple Pay payments.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class ApplePayConfiguration extends PaymentMethodConfigurationBase {

    /**
     * The type of the Apple Pay payment total line item. Default: "final"
     * [Optional]
     */
    @Builder.Default
    private TotalType totalType = TotalType.FINAL;
}
