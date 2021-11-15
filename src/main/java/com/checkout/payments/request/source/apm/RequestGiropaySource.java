package com.checkout.payments.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestGiropaySource extends AbstractRequestSource {

    private String purpose;

    @Builder
    private RequestGiropaySource(final String purpose) {
        super(PaymentSourceType.GIROPAY);
        this.purpose = purpose;
    }

    public RequestGiropaySource() {
        super(PaymentSourceType.GIROPAY);
    }

}