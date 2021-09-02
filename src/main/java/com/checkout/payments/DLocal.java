package com.checkout.payments;

import com.checkout.common.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DLocal {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Payer {
        private String document;
        private String name;
        private String email;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Installments {
        private String count;
    }

    private CountryCode country;
    private Payer payer;
    private Installments installments;

    /**
     * @deprecated Will be removed on a future version
     */
    @Deprecated
    public String getCountry() {
        return Optional.ofNullable(country).map(CountryCode::name).orElse(null);
    }

    /**
     * @deprecated Will be removed on a future version
     */
    @Deprecated
    public void setCountry(final String country) {
        this.country = CountryCode.fromString(country);
    }

    public void setCountry(final CountryCode country) {
        this.country = country;
    }
}