package com.checkout.handlepaymentsandpayouts.payments.common.source.stcpaysource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * stcpay source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class StcpaySource extends AbstractSource {

    /**
     * Initializes a new instance of the StcpaySource class.
     */
    public StcpaySource() {
        super(SourceType.STCPAY);
    }

}
