package com.checkout.handlepaymentsandpayouts.payments.common.source.mbwaysource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * mbway source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class MbwaySource extends AbstractSource {

    /**
     * Initializes a new instance of the MbwaySource class.
     */
    public MbwaySource() {
        super(SourceType.MBWAY);
    }

}
