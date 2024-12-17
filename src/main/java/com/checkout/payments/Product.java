package com.checkout.payments;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public final class Product {

    private ProductType type;

    private String name;

    private Long quantity;

    @SerializedName("unit_price")
    private Long unitPrice;

    private String reference;

    @SerializedName("commodity_code")
    private String commodityCode;

    @SerializedName("unit_of_measure")
    private String unitOfMeasure;

    @SerializedName("total_amount")
    private Long totalAmount;

    @SerializedName("tax_rate")
    private Long taxRate;

    @SerializedName("tax_amount")
    private Long taxAmount;

    @SerializedName("discount_amount")
    private Long discountAmount;

    @SerializedName("wxpay_goods_id")
    private String wxpayGoodsId;

    @SerializedName("image_url")
    private String imageUrl;

    private String url;

    private String sku;

    @SerializedName("service_ends_on")
    private Instant serviceEndsOn;

}
