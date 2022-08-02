package com.checkout.payments;

import com.checkout.common.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class DLocalProcessingSettings {

    private CountryCode country;

    private Payer payer;

    private Installments installments;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Installments {
        private String count;
    }

}