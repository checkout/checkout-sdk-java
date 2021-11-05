package com.checkout.common;

import com.google.gson.annotations.SerializedName;
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

    @SerializedName("sub_entity_id")
    private String subEntityId;

    @SerializedName("sub_entities")
    private List<MarketplaceDataSubEntity> subEntities;

}
