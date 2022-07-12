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
public final class RequestBenefitPaySource extends AbstractRequestSource {

    @SerializedName("integration_type")
    private String integrationType;

    @Builder
    private RequestBenefitPaySource(final String integrationType) {
        super(PaymentSourceType.BENEFITPAY);
        this.integrationType = integrationType;
    }

    public RequestBenefitPaySource() {
        super(PaymentSourceType.BENEFITPAY);
    }

}
