package com.checkout.handlepaymentsandpayouts.payments.common.source.cvconnectsource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * cvconnect source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CvconnectSource extends AbstractSource {

    /**
     * Initializes a new instance of the CvconnectSource class.
     */
    public CvconnectSource() {
        super(SourceType.CVCONNECT);
    }

}
