package com.checkout.handlepaymentsandpayouts.payments.common.source.gcashsource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * gcash source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class GcashSource extends AbstractSource {

    /**
     * Initializes a new instance of the GcashSource class.
     */
    public GcashSource() {
        super(SourceType.GCASH);
    }

}
