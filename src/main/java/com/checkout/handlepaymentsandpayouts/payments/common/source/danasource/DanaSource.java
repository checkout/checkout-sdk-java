package com.checkout.handlepaymentsandpayouts.payments.common.source.danasource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * dana source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class DanaSource extends AbstractSource {

    /**
     * Initializes a new instance of the DanaSource class.
     */
    public DanaSource() {
        super(SourceType.DANA);
    }

}
