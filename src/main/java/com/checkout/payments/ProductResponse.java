package com.checkout.payments;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class ProductResponse {

    @SerializedName("Type")
    private Object type;

    private String name;

    private Long quantity;

    private Long unitPrice;

    private String reference;

    private String commodityCode;

    private String unitOfMeasure;

    private Long totalAmount;

    private Long taxRate;

    private Long taxAmount;

    private Long discountAmount;

    private String wxpayGoodsId;

    private String imageUrl;

    private String url;

    private String sku;

    /**
     * Maximum date for the service to be rendered or ended.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    private LocalDate serviceEndsOn;

    public ProductType getTypeAsEnum() {
        return type instanceof ProductType ? (ProductType) type : null;
    }

    public String getTypeAsString() {
        return type instanceof String ? (String) type : null;
    }

}
