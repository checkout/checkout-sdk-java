package com.checkout.payments.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * paynow source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestPaynowSource extends AbstractRequestSource {

    /**
     * Initializes a new instance of the PaynowSource class.
     */
    public RequestPaynowSource() {
        super(PaymentSourceType.PAYNOW);
    }

}
