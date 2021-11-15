package com.checkout.payments;

import com.checkout.common.CountryCode;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class CardDestinationResponse {

    private String id;

    private String type;

    private String bin;

    private String last4;

    @SerializedName("expiry_month")
    private int expiryMonth;

    @SerializedName("expiry_year")
    private int expiryYear;

    private String scheme;

    @SerializedName("card_type")
    private String cardType;

    @SerializedName("card_category")
    private String cardCategory;

    private String issuer;

    @SerializedName("issuer_country")
    private CountryCode issuerCountry;

    @SerializedName("product_type")
    private String productType;

    @SerializedName("product_id")
    private String productId;

    private String fingerprint;

}
