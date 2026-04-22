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
public final class RequestBizumSource extends AbstractRequestSource {

    /**
     * The customer's mobile number.
     * [Optional]
     * @deprecated Removed from the API on 2025/02/10. Use the customer object instead.
     */
    @Deprecated
    private String mobileNumber;

    @Builder
    private RequestBizumSource(final String mobileNumber) {
        super(PaymentSourceType.BIZUM);
        this.mobileNumber = mobileNumber;
    }

    public RequestBizumSource() {
        super(PaymentSourceType.BIZUM);
    }
}
