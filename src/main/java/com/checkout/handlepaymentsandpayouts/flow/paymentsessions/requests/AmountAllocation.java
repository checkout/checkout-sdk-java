package com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AmountAllocation {

    /**
     * The sub-entity's ID.
     */
    private String id;

    /**
     * The split amount, in minor currency units. The sum of all split amounts must be equal to the payment amount. The
     * split amount will be credited to your sub-entity's currency account.
     */
    private Long amount;

    /**
     * A reference you can use to identify the split. For example, an order number.
     */
    private String reference;

    /**
     * The commission you'd like to collect from the split, calculated using the formula: commission = (amount *
     * commission.percentage) + commission.amount. The commission cannot exceed the split amount. Commission will be
     * credited to your currency account.
     */
    private Commission commission;

}
