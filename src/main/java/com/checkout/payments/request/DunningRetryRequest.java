package com.checkout.payments.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class DunningRetryRequest {

    /**
     * Whether dunning retries are enabled.
     * [Optional]
     */
    private Boolean enabled;

    /**
     * The maximum number of retry attempts.
     * [Optional]
     */
    private Integer maxAttempts;

    /**
     * The number of days after which retries stop.
     * [Optional]
     */
    private Integer endAfterDays;

}
