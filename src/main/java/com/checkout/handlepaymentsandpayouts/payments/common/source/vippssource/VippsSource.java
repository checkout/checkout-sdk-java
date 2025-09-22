package com.checkout.handlepaymentsandpayouts.payments.common.source.vippssource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * vipps source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class VippsSource extends AbstractSource {

    /**
     * Initializes a new instance of the VippsSource class.
     */
    public VippsSource() {
        super(SourceType.VIPPS);
    }

}
