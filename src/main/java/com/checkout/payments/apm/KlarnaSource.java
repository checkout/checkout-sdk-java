package com.checkout.payments.apm;

import com.checkout.apm.klarna.KlarnaProduct;
import com.checkout.common.CountryCode;
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
public final class KlarnaSource implements RequestSource {

    private static final PaymentSourceType type = PaymentSourceType.KLARNA;

    @SerializedName("authorization_token")
    private String authorizationToken;

    private String locale;

    @SerializedName("purchase_country")
    private CountryCode purchaseCountry;

    @SerializedName("tax_amount")
    private Integer taxAmount;

    @SerializedName("billing_address")
    private BillingAddress billingAddress;

    private Customer customer;

    private List<KlarnaProduct> products;

    @Data
    @Builder
    public static class BillingAddress {

        @SerializedName("given_name")
        private String givenName;

        @SerializedName("family_name")
        private String familyName;

        private String email;

        private String title;

        @SerializedName("street_address")
        private String streetAddress;

        @SerializedName("street_address2")
        private String streetAddress2;

        @SerializedName("postal_code")
        private String postalCode;

        private String city;

        private String phone;

        private CountryCode country;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Customer {

        @SerializedName("date_of_birth")
        private String dateOfBirth;

        private String gender;

    }

}
