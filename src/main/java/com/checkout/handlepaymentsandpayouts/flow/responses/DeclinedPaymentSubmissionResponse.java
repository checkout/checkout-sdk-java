package com.checkout.handlepaymentsandpayouts.flow.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Declined payment submission response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DeclinedPaymentSubmissionResponse extends PaymentSubmissionResponse {

    /**
     * The reason for the payment decline.
     */
    private String declineReason;
}