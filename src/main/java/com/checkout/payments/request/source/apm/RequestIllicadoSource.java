package com.checkout.payments.request.source.apm;

import com.checkout.common.Address;
import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestIllicadoSource extends AbstractRequestSource {

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
