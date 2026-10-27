package com.checkout.handlepaymentsandpayouts.flow.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Configures the parameters for customer retries. A customer retry is a payment attempt performed by the
 * customer on the checkout page. This differs from authorization request retries performed in the back end
 * by Checkout.com. Used by hosted payments, payment links and payment sessions.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CustomerRetry {

    /**
     * The maximum number of customer retry attempts, excluding the initial payment attempt.
     * Minimum 0, maximum 5. Default: 5. The SDK sends 5 when not set.
     * [Optional] for hosted payments and payment links; [Required] for payment sessions.
     */
    @Builder.Default
    private Integer maxAttempts = 5;
}
