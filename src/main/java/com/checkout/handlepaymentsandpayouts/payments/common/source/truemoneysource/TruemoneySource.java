package com.checkout.handlepaymentsandpayouts.payments.common.source.truemoneysource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * truemoney source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class TruemoneySource extends AbstractSource {

    /**
     * Initializes a new instance of the TruemoneySource class.
     */
    public TruemoneySource() {
        super(SourceType.TRUEMONEY);
    }

}
