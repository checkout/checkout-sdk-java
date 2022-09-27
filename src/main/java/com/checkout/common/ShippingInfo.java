package com.checkout.common;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ShippingInfo {

    @SerializedName("shipping_company")
    private String shippingCompany;

    @SerializedName("shipping_method")
    private String shippingMethod;

    @SerializedName("tracking_number")
    private String trackingNumber;

    @SerializedName("tracking_uri")
    private String trackingUri;

    @SerializedName("return_shipping_company")
    private String returnShippingCompany;

    @SerializedName("return_tracking_number")
    private String returnTrackingNumber;

    @SerializedName("return_tracking_uri")
    private String returnTrackingUri;

}
