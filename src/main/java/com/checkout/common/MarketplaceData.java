package com.checkout.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class MarketplaceData {

    private String subEntityId;

    private List<MarketplaceDataSubEntity> subEntities;

}
