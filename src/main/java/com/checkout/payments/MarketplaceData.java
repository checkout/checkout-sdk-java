package com.checkout.payments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
public class MarketplaceData {
    @NonNull
    private final String subEntityId;
}
