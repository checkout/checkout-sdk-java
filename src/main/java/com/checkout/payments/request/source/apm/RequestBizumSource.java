package com.checkout.payments.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import com.google.gson.annotations.SerializedName;
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

    @SerializedName("mobile_number")
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