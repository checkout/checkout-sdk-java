package com.checkout.handlepaymentsandpayouts.flow.requests;

import com.checkout.payments.LocaleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
     * Creates a translated version of the page in the specified language. Default: "en-GB"
     */
    @Builder.Default
    private LocaleType locale = LocaleType.EN_GB;

    /**
     * A unique token representing the additional customer data captured by Flow, 
     * as received from the handleSubmit callback.
     * Do not log or store this value.
     */
    private String sessionData;
}