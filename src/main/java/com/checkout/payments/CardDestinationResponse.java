package com.checkout.payments;

import com.checkout.common.CountryCode;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Optional;

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

    /**
     * @deprecated Will be removed on a future version
     */
    @Deprecated
    public String getIssuerCountry() {
        return Optional.ofNullable(issuerCountry).map(CountryCode::name).orElse(null);
    }

    /**
     * @deprecated Will be removed on a future version
     */
    @Deprecated
    public void setIssuerCountry(final String issuerCountry) {
        this.issuerCountry = CountryCode.fromString(issuerCountry);
    }

    public void setIssuerCountry(final CountryCode issuerCountry) {
        this.issuerCountry = issuerCountry;
    }

}
