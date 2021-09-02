package com.checkout.instruments;

import com.checkout.common.CountryCode;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Optional;

@Data
public class CreateInstrumentResponse {

    private String id;

    private String type;

    private String fingerprint;

    @SerializedName("expiry_month")
    private Integer expiryMonth;

    @SerializedName("expiry_year")
    private Integer expiryYear;

    private String scheme;

    @SerializedName("last4")
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

    private CustomerResponse customer;

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
