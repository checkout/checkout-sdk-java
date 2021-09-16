package com.checkout.payments.apm;

import com.checkout.common.CountryCode;
import com.checkout.common.PaymentSourceType;
import com.checkout.payments.RequestSource;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class BoletoSource implements RequestSource {

    private final PaymentSourceType type = PaymentSourceType.BOLETO;

    @SerializedName("integration_type")
    private IntegrationType integrationType;

    private CountryCode country;

    private Payer payer;

    private String description;

}