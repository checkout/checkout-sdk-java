package com.checkout.apm.klarna;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class KlarnaProduct {

    private String name;

    private Long quantity;

    @SerializedName("unit_price")
    private Long unitPrice;

    @SerializedName("tax_rate")
    private Long taxRate;

    @SerializedName("total_amount")
    private Long totalAmount;

    @SerializedName("total_tax_amount")
    private Long totalTaxAmount;

}
