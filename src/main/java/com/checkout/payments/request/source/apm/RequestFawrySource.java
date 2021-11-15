package com.checkout.payments.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestFawrySource extends AbstractRequestSource {

    private String description;

    @SerializedName("customer_mobile")
    private String customerMobile;

    @SerializedName("customer_email")
    private String customerEmail;

    private List<Product> products;

    @Builder
    private RequestFawrySource(final String description,
                               final String customerMobile,
                               final String customerEmail,
                               final List<Product> products) {
        super(PaymentSourceType.FAWRY);
        this.description = description;
        this.customerMobile = customerMobile;
        this.customerEmail = customerEmail;
        this.products = products;
    }

    public RequestFawrySource() {
        super(PaymentSourceType.FAWRY);
    }

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