package com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentMethodConfiguration {

    /**
     * Configuration options specific to Apple Pay payments.
     */
    private Applepay applepay;

    /**
     * Configuration options specific to card payments.
     */
    private Card card;

    /**
     * Configuration options specific to Google Pay payments.
     */
    private Googlepay googlepay;

}
