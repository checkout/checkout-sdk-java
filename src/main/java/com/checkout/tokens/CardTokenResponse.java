package com.checkout.tokens;

import com.checkout.common.Address;
import com.checkout.common.Phone;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CardTokenResponse extends TokenResponse {

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

    private String bin;

    @SerializedName("card_type")
    private String cardType;

    @SerializedName("card_category")
    private String cardCategory;

    private String issuer;

    @SerializedName("issuer_country")
    private String issuerCountry;

    @SerializedName("product_id")
    private String productId;

    @SerializedName("product_type")
    private String productType;

}