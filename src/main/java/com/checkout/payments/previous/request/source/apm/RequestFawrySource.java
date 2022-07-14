package com.checkout.payments.previous.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.previous.request.source.AbstractRequestSource;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestFawrySource extends AbstractRequestSource {

    private String description;

    @SerializedName("customer_profile_id")
    private String customerProfileId;

    @SerializedName("customer_email")
    private String customerEmail;

    @SerializedName("customer_mobile")
    private String customerMobile;

    @SerializedName("expires_on")
    private Instant expiresOn;

    private List<Product> products;

    @Builder
    private RequestFawrySource(final String description,
                              final String customerProfileId,
                              final String customerEmail,
                              final String customerMobile,
                              final Instant expiresOn,
                              final List<Product> products) {
        super(PaymentSourceType.FAWRY);
        this.description = description;
        this.customerProfileId = customerProfileId;
        this.customerEmail = customerEmail;
        this.customerMobile = customerMobile;
        this.expiresOn = expiresOn;
        this.products = products;
    }

    @Builder


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