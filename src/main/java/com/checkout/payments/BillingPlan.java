package com.checkout.payments;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class BillingPlan {

    private BillingPlanType type;

    @SerializedName("skip_shipping_address")
    private Boolean skipShippingAddress;

    @SerializedName("immutable_shipping_address")
    private Boolean immutableShippingAddress;
}
