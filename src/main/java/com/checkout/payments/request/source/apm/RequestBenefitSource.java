package com.checkout.payments.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Benefit source.
 *
 * <p>The current specification's payment request source
 * list does not declare this source. It is retained for backwards compatibility.
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestBenefitSource extends AbstractRequestSource {

    public RequestBenefitSource() {
        super(PaymentSourceType.BENEFIT);
    }
}
