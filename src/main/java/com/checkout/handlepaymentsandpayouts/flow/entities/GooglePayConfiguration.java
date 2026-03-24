package com.checkout.handlepaymentsandpayouts.flow.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Configuration options specific to Google Pay payments.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class GooglePayConfiguration extends PaymentMethodConfigurationBase {

    /**
     * The status of the Google Pay payment total price. Default: "final"
     */
    @Builder.Default
    private TotalPriceStatus totalPriceStatus = TotalPriceStatus.FINAL;
}