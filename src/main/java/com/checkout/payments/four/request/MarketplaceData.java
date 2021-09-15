package com.checkout.payments.four.request;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class MarketplaceData {

    @SerializedName("sub_entity_id")
    private final String subEntityId;

}
