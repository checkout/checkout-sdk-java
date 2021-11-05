package com.checkout.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class MarketplaceDataSubEntity {

    private String id;

    private Long amount;

    private String reference;

    private MarketplaceCommission commission;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class MarketplaceCommission {

        private Long amount;

        private Double percentage;

    }
}
