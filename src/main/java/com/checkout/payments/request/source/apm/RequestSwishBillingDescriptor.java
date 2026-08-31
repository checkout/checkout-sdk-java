package com.checkout.payments.request.source.apm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The billing descriptor for a Swish payment.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class RequestSwishBillingDescriptor {

    /**
     * A description for the payment, which displays on the customer's statement.
     * [Required]
     * max 120 characters
     */
    private String name;
}