package com.checkout.handlepaymentsandpayouts.payments.common.source.afterpaysource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * afterpay source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class AfterpaySource extends AbstractSource {

    /**
     * Initializes a new instance of the AfterpaySource class.
     */
    public AfterpaySource() {
        super(SourceType.AFTERPAY);
    }

}
