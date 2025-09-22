package com.checkout.handlepaymentsandpayouts.payments.common.source.kakaopaysource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * kakaopay source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class KakaopaySource extends AbstractSource {

    /**
     * Initializes a new instance of the KakaopaySource class.
     */
    public KakaopaySource() {
        super(SourceType.KAKAOPAY);
    }

}
