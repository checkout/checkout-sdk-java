package com.checkout.payments.contexts;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentContextsItems {

    private String type;

    private String name;

    private Integer quantity;

    @SerializedName("unit_price")
    private Integer unitPrice;

    private String reference;

    @SerializedName("total_amount")
    private Integer totalAmount;

    @SerializedName("tax_rate")
    private Integer taxRate;

    @SerializedName("tax_amount")
    private Integer taxAmount;

    @SerializedName("discount_amount")
    private Integer discountAmount;

    @SerializedName("wxpay_goods_id")
    private String wxpayGoodsId;

    private String url;

    @SerializedName("image_url")
    private String imageUrl;

    @SerializedName("service_ends_on")
    private String serviceEndsOn;

}
