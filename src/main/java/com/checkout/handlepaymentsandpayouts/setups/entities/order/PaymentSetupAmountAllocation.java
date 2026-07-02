package com.checkout.handlepaymentsandpayouts.setups.entities.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * An amount allocation representing a sub-entity on whose behalf the payment is being processed.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentSetupAmountAllocation {

    /**
     * The id of the sub-entity.
     * [Required]
     */
    private String id;

    /**
     * The split amount - this will be credited to your sub-entity's currency account. The sum of all
     * split amounts must be equal to the payment amount. Provided in the minor currency unit.
     * [Required]
     * min 0, max 9999999999
     */
    private Long amount;

    /**
     * A reference you can later use to identify this split, such as an order number.
     * [Optional]
     * max 50 characters
     */
    private String reference;

    /**
     * Commission you'd like to collect from this split.
     * [Optional]
     */
    private AmountAllocationCommission commission;
}
