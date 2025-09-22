package com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.processing;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * processing
 * Returns information related to the processing of the payment.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Processing {

    /**
     * The speed at which the unreferenced refund is processed.
     * Only applicable for unreferenced refunds.
     * [Optional]
     */
    @SerializedName("processing_speed")
    private String processingSpeed;

}
