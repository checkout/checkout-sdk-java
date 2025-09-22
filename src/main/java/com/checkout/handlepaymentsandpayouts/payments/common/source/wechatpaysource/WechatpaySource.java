package com.checkout.handlepaymentsandpayouts.payments.common.source.wechatpaysource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * wechatpay source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class WechatpaySource extends AbstractSource {

    /**
     * Initializes a new instance of the WechatpaySource class.
     */
    public WechatpaySource() {
        super(SourceType.WECHATPAY);
    }

}
