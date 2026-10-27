package com.checkout.payments.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Configuration of Dunning retries.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class DunningRetryRequest {

    /**
     * Indicates if Checkout.com retries the payment when it's declined with a supported decline code.
     * [Required]
     */
    private Boolean enabled;

    /**
     * The maximum number of authorization retry attempts, excluding the initial authorization.
     * Minimum 1, maximum 15. Default: 6.
     * [Optional]
     */
    private Integer maxAttempts;

    /**
     * The maximum number of days between the initial request and the final retry attempt.
     * Minimum 1, maximum 60. Default: 30.
     * [Optional]
     */
    private Integer endAfterDays;

}
