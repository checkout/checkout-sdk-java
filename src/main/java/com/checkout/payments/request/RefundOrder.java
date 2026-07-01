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
public final class RefundOrder {

    private String name;

    private Long quantity;

    private Long unitPrice;

    private String reference;

    private String commodityCode;

    private String unitOfMeasure;

    private Long totalAmount;

    private Long taxRate;

    private Long taxAmount;

    @SerializedName("doscount_amount")
    private Long discountAmount;

    @SerializedName("w_xpay_good_id")
    private String wxpayGoodsId;

    private String imageUrl;

    private String url;

    private String type;

    private Instant ServiceEndsOn;

}
