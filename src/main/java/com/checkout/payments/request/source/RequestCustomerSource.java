package com.checkout.payments.request.source;

import com.checkout.common.PaymentSourceType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestCustomerSource extends AbstractRequestSource {

    private String id;

    @Builder
    private RequestCustomerSource(final String id) {
        super(PaymentSourceType.CUSTOMER);
        this.id = id;
    }

    public RequestCustomerSource() {
        super(PaymentSourceType.CUSTOMER);
    }

}
