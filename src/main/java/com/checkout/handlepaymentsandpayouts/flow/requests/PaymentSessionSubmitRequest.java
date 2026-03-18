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
 * Request to submit a payment session.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PaymentSessionSubmitRequest extends PaymentSessionBase {

    /**
     * A unique token representing the additional customer data captured by Flow, 
     * as received from the handleSubmit callback.
     * Do not log or store this value.
     */
    private String sessionData;

    /**
     * Deprecated - The Customer's IP address. Only IPv4 and IPv6 addresses are accepted.
     */
    @Deprecated
    private String ipAddress;
}