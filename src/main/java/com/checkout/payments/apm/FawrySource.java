package com.checkout.payments.apm;

import com.checkout.payments.RequestSource;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public final class FawrySource implements RequestSource {

    private final String type = "fawry";

    private final String description;

    @SerializedName("customer_mobile")
    private final String customerMobile;

    @SerializedName("customer_email")
    private final String customerEmail;

    private final List<Product> products;

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

    @Override
    public String getType() {
        return type;
    }

}