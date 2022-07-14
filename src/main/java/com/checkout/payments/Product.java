package com.checkout.payments;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class Product {

    private String name;

    private Long quantity;

    @SerializedName("unit_price")
    private Long unitPrice;

    private String reference;

    @SerializedName("image_url")
    private String imageUrl;

    private String url;

    @SerializedName("total_amount")
    private Long totalAmount;

    @SerializedName("tax_amount")
    private Long taxAmount;

    @SerializedName("discount_amount")
    private Long discountAmount;

    private String sku;

    @SerializedName("goods_id")
    private String goodsId;

    @SerializedName("wxpay_goods_id")
    private String wxpayGoodsId;

}
