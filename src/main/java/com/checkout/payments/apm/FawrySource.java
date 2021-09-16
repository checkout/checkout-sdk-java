package com.checkout.payments.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.RequestSource;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class FawrySource implements RequestSource {

    private final PaymentSourceType type = PaymentSourceType.FAWRY;

    private String description;

    @SerializedName("customer_mobile")
    private String customerMobile;

    @SerializedName("customer_email")
    private String customerEmail;

    private List<Product> products;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Product {

        @SerializedName("product_id")
        private String id;

        private Long quantity;

        private Long price;

        private String description;

    }

}