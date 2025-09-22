package com.checkout.handlepaymentsandpayouts.payments.common.source.tngsource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * tng source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class TngSource extends AbstractSource {

    /**
     * Initializes a new instance of the TngSource class.
     */
    public TngSource() {
        super(SourceType.TNG);
    }

}
