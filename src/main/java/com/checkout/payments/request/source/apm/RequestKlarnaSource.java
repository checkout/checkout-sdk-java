package com.checkout.payments.request.source.apm;

import com.checkout.apm.klarna.KlarnaProduct;
import com.checkout.common.CountryCode;
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
public final class RequestKlarnaSource extends AbstractRequestSource {

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

    @Builder
    private RequestKlarnaSource(final String authorizationToken,
                                final String locale,
                                final CountryCode purchaseCountry,
                                final Integer taxAmount,
                                final BillingAddress billingAddress,
                                final Customer customer,
                                final List<KlarnaProduct> products) {
        super(PaymentSourceType.KLARNA);
        this.authorizationToken = authorizationToken;
        this.locale = locale;
        this.purchaseCountry = purchaseCountry;
        this.taxAmount = taxAmount;
        this.billingAddress = billingAddress;
        this.customer = customer;
        this.products = products;
    }

    public RequestKlarnaSource() {
        super(PaymentSourceType.KLARNA);
    }

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
