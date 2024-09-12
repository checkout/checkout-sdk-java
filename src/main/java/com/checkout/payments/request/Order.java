package com.checkout.payments.request;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Order {

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

    @SerializedName("doscount_amount")
    private Long discountAmount;

    @SerializedName("w_xpay_good_id")
    private String wxpayGoodsId;

    @SerializedName("image_url")
    private String imageUrl;

    private String url;

    private String type;

    @SerializedName("service_ends_on")
    private Instant ServiceEndsOn;

}
