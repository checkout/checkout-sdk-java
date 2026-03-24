package com.checkout.handlepaymentsandpayouts.flow.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;

/**
 * Request to create and complete a payment session in one operation.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class PaymentSessionCompleteRequest extends PaymentSessionInfo {

    /**
     * A unique token representing the additional customer data captured by Flow, 
     * as received from the handleSubmit callback.
     * Do not log or store this value.
     */
    private String sessionData;
}