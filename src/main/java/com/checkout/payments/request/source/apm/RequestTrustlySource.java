package com.checkout.payments.request.source.apm;

import com.checkout.common.Address;
import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @deprecated Trustly was removed as a payment method on 2024/09/17.
 */
@Deprecated
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestTrustlySource extends AbstractRequestSource {

    /**
     * The billing address for the Trustly payment.
     * [Optional]
     */
    private Address billingAddress;

    @Builder
    private RequestTrustlySource(final Address billingAddress) {
        super(PaymentSourceType.TRUSTLY);
        this.billingAddress = billingAddress;
    }

    @Deprecated
    public RequestTrustlySource() {
        super(PaymentSourceType.TRUSTLY);
    }
}
