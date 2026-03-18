package com.checkout.handlepaymentsandpayouts.flow.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Configuration for asynchronous retries
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRetry {

    /**
     * The maximum number of authorization retry attempts, excluding the initial authorization. Default: 5
     */
    @Builder.Default
    private Integer maxAttempts = 5;
}