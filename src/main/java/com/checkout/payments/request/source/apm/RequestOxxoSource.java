package com.checkout.payments.request.source.apm;

import com.checkout.common.CountryCode;
import com.checkout.common.PaymentSourceType;
import com.checkout.payments.Payer;
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
public final class RequestOxxoSource extends AbstractRequestSource {

    @SerializedName("integration_type")
    private final IntegrationType integrationType = IntegrationType.REDIRECT;

    private CountryCode country;

    private Payer payer;

    private String description;

    @Builder
    private RequestOxxoSource(final CountryCode country,
                              final Payer payer,
                              final String description) {
        super(PaymentSourceType.OXXO);
        this.country = country;
        this.payer = payer;
        this.description = description;
    }

    public RequestOxxoSource() {
        super(PaymentSourceType.OXXO);
    }

}
