package com.checkout.handlepaymentsandpayouts.payments.common.source.mobilepaysource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * mobilepay source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class MobilepaySource extends AbstractSource {

    /**
     * Initializes a new instance of the MobilepaySource class.
     */
    public MobilepaySource() {
        super(SourceType.MOBILEPAY);
    }

}
