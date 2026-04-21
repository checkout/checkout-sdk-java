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

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestFawrySource extends AbstractRequestSource {

    /**
     * The description of the payment.
     * [Optional]
     */
    private String description;

    /**
     * The customer's profile ID.
     * [Optional]
     */
    @SerializedName("customer_profile_id")
    private String customerProfileId;

    /**
     * The customer's email address.
     * [Optional]
     */
    @SerializedName("customer_email")
    private String customerEmail;

    /**
     * The customer's mobile number.
     * [Optional]
     */
    @SerializedName("customer_mobile")
    private String customerMobile;

    /**
     * The timestamp after which the payment expires.
     * [Optional]
     * Format: date-time (ISO 8601)
     */
    @SerializedName("expires_on")
    private Instant expiresOn;

    /**
     * The products included in the order.
     * [Optional]
     */
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

    public RequestFawrySource() {
        super(PaymentSourceType.FAWRY);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Product {

        /**
         * The product identifier.
         * [Optional]
         */
        @SerializedName("product_id")
        private String id;

        /**
         * The quantity of the product.
         * [Optional]
         */
        private Long quantity;

        /**
         * The price of the product in minor currency units.
         * [Optional]
         */
        private Long price;

        /**
         * The description of the product.
         * [Optional]
         */
        private String description;

    }

}
