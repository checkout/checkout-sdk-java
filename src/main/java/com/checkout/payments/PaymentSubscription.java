package com.checkout.payments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The details of the subscription.
 * <p>
 * [Optional]
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSubscription {

    /**
     * The ID or reference linking a series of recurring payments together.
     * <p>
     * [Optional]
     * </p>
     * Length: &lt;= 50 characters
     */
    private String id;
}
