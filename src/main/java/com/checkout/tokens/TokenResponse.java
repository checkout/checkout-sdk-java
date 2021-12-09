package com.checkout.tokens;

import com.checkout.common.CardCategory;
import com.checkout.common.CardType;
import com.checkout.common.CountryCode;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.time.Instant;

@Data
public class TokenResponse {

    private TokenType type;

    private String token;

    @SerializedName("expires_on")
    private Instant expiresOn;

    @SerializedName("expiry_month")
    private Integer expiryMonth;

    @SerializedName("expiry_year")
    private Integer expiryYear;

    private String scheme;

    private String last4;

    private String bin;

    @SerializedName("card_type")
    private CardType cardType;

    @SerializedName("card_category")
    private CardCategory cardCategory;

    private String issuer;

    @SerializedName("issuer_country")
    private CountryCode issuerCountry;

    @SerializedName("product_id")
    private String productId;

    @SerializedName("product_type")
    private String productType;

    @SerializedName("token_format")
    private String tokenFormat;

}
