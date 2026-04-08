package com.checkout.payments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Controls processor attempts at the payment level.
 * <p>
 * [Optional]
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRouting {

    /**
     * Specifies the processing rules for the payment.
     * <p>
     * [Optional]
     * </p>
     */
    private List<RoutingAttempt> attempts;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoutingAttempt {

        /**
         * The card scheme to use for the payment attempt.
         * <p>
         * [Optional]
         * </p>
         */
        private PaymentRoutingScheme scheme;
    }
}
