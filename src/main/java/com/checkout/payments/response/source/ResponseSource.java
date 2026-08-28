package com.checkout.payments.response.source;

import com.checkout.common.PaymentSourceType;

/**
 * The payment source returned on a payment response.
 *
 * <p>Implemented by the typed variants the specification declares, and by
 * {@link AlternativePaymentSourceResponse} for any source type the SDK does not model.
 */
public interface ResponseSource {

    /**
     * Returns the payment source type.
     *
     * @return the payment source type.
     */
    PaymentSourceType getType();

}
