package com.checkout.handlepaymentsandpayouts.payments.common.source.postfinancesource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * postfinance source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PostfinanceSource extends AbstractSource {

    /**
     * Initializes a new instance of the PostfinanceSource class.
     */
    public PostfinanceSource() {
        super(SourceType.POSTFINANCE);
    }

}
