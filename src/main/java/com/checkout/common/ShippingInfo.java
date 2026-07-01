package com.checkout.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ShippingInfo {

    private String shippingCompany;

    private String shippingMethod;

    private String trackingNumber;

    private String trackingUri;

    private String returnShippingCompany;

    private String returnTrackingNumber;

    private String returnTrackingUri;

}
