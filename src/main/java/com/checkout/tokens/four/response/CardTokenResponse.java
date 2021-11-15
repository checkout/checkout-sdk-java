package com.checkout.tokens.four.response;

import com.checkout.common.CardCategory;
import com.checkout.common.CountryCode;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CardTokenResponse extends TokenResponse {

    @SerializedName("expiry_month")
    private Integer expiryMonth;

    @SerializedName("expiry_year")
    private Integer expiryYear;

    private String scheme;

    private String last4;

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

}
