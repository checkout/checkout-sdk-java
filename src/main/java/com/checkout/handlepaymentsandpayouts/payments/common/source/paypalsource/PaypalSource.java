package com.checkout.handlepaymentsandpayouts.payments.common.source.paypalsource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * paypal source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaypalSource extends AbstractSource {

    /**
     * Initializes a new instance of the PaypalSource class.
     */
    public PaypalSource() {
        super(SourceType.PAYPAL);
    }

}
