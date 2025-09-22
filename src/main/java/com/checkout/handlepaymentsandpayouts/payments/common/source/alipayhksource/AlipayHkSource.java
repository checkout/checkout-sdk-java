package com.checkout.handlepaymentsandpayouts.payments.common.source.alipayhksource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * alipay_hk source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class AlipayHkSource extends AbstractSource {

    /**
     * Initializes a new instance of the AlipayHkSource class.
     */
    public AlipayHkSource() {
        super(SourceType.ALIPAY_HK);
    }

}
