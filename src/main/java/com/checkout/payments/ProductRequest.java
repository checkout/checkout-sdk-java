package com.checkout.payments;

import com.checkout.common.CountryCode;
import com.checkout.payments.request.ItemSubType;
import com.checkout.payments.request.ItemType;
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
public final class ProductRequest {

    private ItemType type;

    @SerializedName("sub_type")
    private ItemSubType subType;

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

    @SerializedName("purchase_country")
    private CountryCode purchaseCountry;

    @SerializedName("foreign_retailer_amount")
    private Long foreignRetailerAmount;

}
