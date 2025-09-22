package com.checkout.handlepaymentsandpayouts.payments.common.source.paymentresponsesourcesource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * PaymentResponseSource source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentResponseSourceSource extends AbstractSource {

    /**
     * Initializes a new instance of the PaymentResponseSourceSource class.
     */
    public PaymentResponseSourceSource() {
        super(SourceType.PAYMENT_RESPONSE_SOURCE);
    }

}
