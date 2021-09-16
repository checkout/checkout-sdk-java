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
public final class BalotoSource implements RequestSource {

    private final PaymentSourceType type = PaymentSourceType.BALOTO;

    @SerializedName("integration_type")
    private final IntegrationType integrationType = IntegrationType.REDIRECT;

    private CountryCode country;

    private Payer payer;

    private String description;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Payer {
        private String name;
        private String email;
    }

}