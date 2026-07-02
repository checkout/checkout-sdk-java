package com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.retry;

import lombok.Builder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * retry
 * Configuration relating to asynchronous retries
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Retry {

    /**
     * Indicates whether asynchronous retries are enabled for the payment.
     * [Optional]
     */
    private Boolean enabled;

    /**
     * Default: 6 The maximum number of authorization retry attempts, excluding the initial authorization.
     * [Optional]
     * [ 1 .. 15 ]
     */
    @Builder.Default
    private Integer maxAttempts = 6;

    /**
     * A timestamp that details the date on which the retry schedule expires, in ISO 8601 format.
     * [Optional]
     */
    private Instant endsOn;

    /**
     * A timestamp of the date on which the next authorization attempt will take place, in ISO 8601 format.
     * [Optional]
     */
    private Instant nextAttemptOn;

}
