package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class MerchantRiskInfo {

    @SerializedName("delivery_email")
    private String deliveryEmail;

    @SerializedName("delivery_timeframe")
    private DeliveryTimeframe deliveryTimeframe;

    @SerializedName("is_preorder")
    private Boolean isPreorder;

    @SerializedName("is_reorder")
    private Boolean isReorder;

    @SerializedName("shipping_indicator")
    private ShippingIndicator shippingIndicator;

}
