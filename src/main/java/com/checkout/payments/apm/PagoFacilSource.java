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
public final class PagoFacilSource implements RequestSource {

    private final PaymentSourceType type = PaymentSourceType.PAGO_FACIL;

    @SerializedName("integration_type")
    private final IntegrationType integrationType = IntegrationType.REDIRECT;

    private CountryCode country;

    private Payer payer;

    private String description;

}
