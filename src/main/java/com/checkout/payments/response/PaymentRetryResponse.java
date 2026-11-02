package com.checkout.payments.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Configuration relating to asynchronous retries. Returned by request a payment and by get payment details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentRetryResponse {

    /**
     * Indicates whether asynchronous retries are enabled for the payment.
     * [Required]
     */
    private Boolean enabled;

    /**
     * The number of retries attempted so far. Minimum 0. Returned by get payment details only.
     * [Optional]
     */
    private Integer attemptsMade;

    /**
     * The maximum number of authorization retry attempts, excluding the initial authorization.
     * Minimum 1, maximum 15. Default: 6.
     * [Optional]
     */
    private Integer maxAttempts;

    /**
     * A timestamp that details the date on which the retry schedule expires, in ISO 8601 format.
     * [Optional]
     */
    private Instant endsOn;

    /**
     * A timestamp that details the date on which the next authorization attempt will take place, in ISO 8601 format.
     * [Optional]
     */
    private Instant nextAttemptOn;

}
