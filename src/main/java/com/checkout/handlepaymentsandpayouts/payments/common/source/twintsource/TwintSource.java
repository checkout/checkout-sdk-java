package com.checkout.handlepaymentsandpayouts.payments.common.source.twintsource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * twint source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class TwintSource extends AbstractSource {

    /**
     * Initializes a new instance of the TwintSource class.
     */
    public TwintSource() {
        super(SourceType.TWINT);
    }

}
