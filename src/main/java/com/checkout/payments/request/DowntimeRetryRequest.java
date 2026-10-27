package com.checkout.payments.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Configuration of Downtime retries.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class DowntimeRetryRequest {

    /**
     * Indicates if Checkout.com retries the payment when it's declined due to issuer or acquirer downtime.
     * [Required]
     */
    private Boolean enabled;

}
