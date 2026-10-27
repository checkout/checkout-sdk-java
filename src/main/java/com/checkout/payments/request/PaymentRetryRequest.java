package com.checkout.payments.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Configuration of asynchronous retries.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class PaymentRetryRequest {

    /**
     * Indicates whether asynchronous retries are enabled for the payment.
     * [Required]
     */
    private Boolean enabled;

    /**
     * Configuration of asynchronous Dunning retries.
     * [Optional]
     */
    private DunningRetryRequest dunning;

    /**
     * Configuration of asynchronous Downtime retries.
     * [Optional]
     */
    private DowntimeRetryRequest downtime;

    /**
     * Not part of the retry object in the API. If set, it is still sent at the top level of retry,
     * where the API does not read it.
     * [Optional]
     *
     * @deprecated Use {@link DunningRetryRequest#getMaxAttempts()} via {@link #dunning} instead.
     * This property will be removed in the next major version.
     */
    @Deprecated
    private Integer maxAttempts;

    /**
     * Not part of the retry object in the API. If set, it is still sent at the top level of retry,
     * where the API does not read it.
     * [Optional]
     *
     * @deprecated Use {@link DunningRetryRequest#getEndAfterDays()} via {@link #dunning} instead.
     * This property will be removed in the next major version.
     */
    @Deprecated
    private Integer endAfterDays;

}
