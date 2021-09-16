package com.checkout.apm.klarna;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class KlarnaProduct {

    private String name;

    private Integer quantity;

    @SerializedName("unit_price")
    private Integer unitPrice;

    @SerializedName("tax_rate")
    private Integer taxRate;

    @SerializedName("total_amount")
    private Integer totalAmount;

    @SerializedName("total_tax_amount")
    private Integer totalTaxAmount;

}
