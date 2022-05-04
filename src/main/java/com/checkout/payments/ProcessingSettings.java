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
public final class ProcessingSettings {

    private boolean aft;

    private DLocalProcessingSettings dlocal;

    @SerializedName("tax_amount")
    private Long taxAmount;

    @SerializedName("shipping_amount")
    private Long shippingAmount;

}