package com.checkout.payments.previous.request.source.apm;

import com.checkout.common.CountryCode;
import com.checkout.common.PaymentSourceType;
import com.checkout.payments.Payer;
import com.checkout.payments.previous.request.source.AbstractRequestSource;
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
public final class RequestBoletoSource extends AbstractRequestSource {

    @SerializedName("integration_type")
    private IntegrationType integrationType;

    private CountryCode country;

    private Payer payer;

    private String description;

    @Builder
    private RequestBoletoSource(final IntegrationType integrationType,
                                final CountryCode country,
                                final Payer payer,
                                final String description) {
        super(PaymentSourceType.BOLETO);
        this.integrationType = integrationType;
        this.country = country;
        this.payer = payer;
        this.description = description;
    }

    public RequestBoletoSource() {
        super(PaymentSourceType.BOLETO);
    }

}
