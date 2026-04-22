package com.checkout.handlepaymentsandpayouts.flow.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Approved payment submission response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class ApprovedPaymentSubmissionResponse extends PaymentSubmissionResponse {

    /**
     * The Payment Sessions unique identifier (only present in CreateAndSubmitPaymentSession response).
     * [Optional]
     */
    private String paymentSessionId;

    /**
     * The secret used by Flow to authenticate payment session requests (only present in CreateAndSubmitPaymentSession response).
     * Do not log or store this value.
     * [Optional]
     */
    private String paymentSessionSecret;
}
