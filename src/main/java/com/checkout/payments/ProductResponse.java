package com.checkout.payments;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class ProductResponse {

    @SerializedName("Type")
    private Object type;

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

    public ProductType getTypeAsEnum() {
        return type instanceof ProductType ? (ProductType) type : null;
    }

    public String getTypeAsString() {
        return type instanceof String ? (String) type : null;
    }

}
