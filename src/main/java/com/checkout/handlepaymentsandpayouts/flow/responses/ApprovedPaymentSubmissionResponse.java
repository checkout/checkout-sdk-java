package com.checkout.handlepaymentsandpayouts.flow.responses;

import com.google.gson.annotations.SerializedName;
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
public class ApprovedPaymentSubmissionResponse extends PaymentSubmissionResponse {

    /**
     * The Payment Sessions unique identifier (only present in CreateAndSubmitPaymentSession response)
     */
    private String paymentSessionId;

    /**
     * The secret used by Flow to authenticate payment session requests (only present in CreateAndSubmitPaymentSession response).
     * Do not log or store this value.
     */
    private String paymentSessionSecret;
}