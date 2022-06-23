package com.checkout.payments;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Processing {

    private Boolean aft;

    private DLocal dlocal;

    @SerializedName("tax_amount")
    private Long taxAmount;

    @SerializedName("shipping_amount")
    private Long shippingAmount;

    @SerializedName("preferred_scheme")
    private PreferredSchema preferredScheme;

    @SerializedName("product_type")
    private ProductType productType;

    @SerializedName("open_id")
    private String openId;

    @SerializedName("original_order_amount")
    private Long originalOrderAmount;

    //Deprecated fields

    @SerializedName("receipt_id")
    private String receiptId;

    private String mid;

    @SerializedName("senderInformation")
    private SenderInformation senderInformation;

    @SerializedName("skipExpiry")
    private Boolean skipExpiry;

}