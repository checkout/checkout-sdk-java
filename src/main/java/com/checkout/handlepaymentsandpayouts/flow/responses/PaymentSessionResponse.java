package com.checkout.handlepaymentsandpayouts.flow.responses;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Payment session response containing session details
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class PaymentSessionResponse extends Resource {

    /**
     * The Payment Sessions unique identifier
     */
    private String id;

    /**
     * A unique token representing the payment session, which you must provide when you initialize Flow.
     * Do not log or store this value.
     */
    private String paymentSessionToken;

    /**
     * The secret used by Flow to authenticate payment session requests.
     * Do not log or store this value.
     */
    private String paymentSessionSecret;
}