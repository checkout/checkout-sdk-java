package com.checkout.payments.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public final class PaymentRetryResponse {

    /**
     * Indicates whether asynchronous retries are enabled for the payment.
     * [Optional]
     */
    private Boolean enabled;

    private Integer maxAttempts;

    private Instant endsOn;

    private Instant nextAttemptOn;

}
