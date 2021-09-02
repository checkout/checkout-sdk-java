package com.checkout.payments.apm;

import com.checkout.common.CountryCode;
import com.checkout.payments.RequestSource;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public final class BoletoSource implements RequestSource {

    private final String type = "boleto";

    @SerializedName("integration_type")
    private final IntegrationType integrationType;

    private final CountryCode country;

    private final Payer payer;

    private final String description;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Payer {
        private String name;
        private String email;
        private String document;
    }

    @Override
    public String getType() {
        return type;
    }

}