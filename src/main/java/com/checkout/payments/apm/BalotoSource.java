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
public final class BalotoSource implements RequestSource {

    private final String type = "baloto";

    @SerializedName("integration_type")
    private final IntegrationType integrationType = IntegrationType.REDIRECT;

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
    }

    @Override
    public String getType() {
        return type;
    }

}