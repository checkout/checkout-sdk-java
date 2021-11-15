package com.checkout.payments;

import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Phone;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardSourceResponse implements ResponseSource {

    private String id;

    private String type;

    @SerializedName("billing_address")
    private Address billingAddress;

    private Phone phone;

    @SerializedName("expiry_month")
    private int expiryMonth;

    @SerializedName("expiry_year")
    private int expiryYear;

    private String name;

    private String scheme;

    private String last4;

    private String fingerprint;

    private String bin;

    @SerializedName("card_type")
    private String cardType;

    @SerializedName("card_category")
    private String cardCategory;

    private String issuer;

    @SerializedName("issuer_country")
    private CountryCode issuerCountry;

    @SerializedName("product_id")
    private String productId;

    @SerializedName("product_type")
    private String productType;

    @SerializedName("avs_check")
    private String avsCheck;

    @SerializedName("cvv_check")
    private String cvvCheck;

    private Boolean payouts;

    @SerializedName("fast_funds")
    private String fastFunds;

    @SerializedName("payment_account_reference")
    private String paymentAccountReference;

    @Override
    public String getType() {
        return type;
    }

}
