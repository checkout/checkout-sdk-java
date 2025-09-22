package com.checkout.handlepaymentsandpayouts.payments.common.source.paynowsource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * paynow source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaynowSource extends AbstractSource {

    /**
     * Initializes a new instance of the PaynowSource class.
     */
    public PaynowSource() {
        super(SourceType.PAYNOW);
    }

}
