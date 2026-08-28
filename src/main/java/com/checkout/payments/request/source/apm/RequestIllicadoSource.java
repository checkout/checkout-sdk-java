package com.checkout.payments.request.source.apm;

import com.checkout.common.Address;
import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import lombok.*;

/**
 * Illicado source.
 *
 * <p>The current specification's payment request source
 * list does not declare this source. It is retained for backwards compatibility.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestIllicadoSource extends AbstractRequestSource {

    /**
     * The customer's billing address.
     * [Optional]
     */
    private Address billingAddress;

    @Builder
    private RequestIllicadoSource(final Address billingAddress) {
        super(PaymentSourceType.ILLICADO);
        this.billingAddress = billingAddress;
    }

    public RequestIllicadoSource() {
        super(PaymentSourceType.ILLICADO);
    }

}
