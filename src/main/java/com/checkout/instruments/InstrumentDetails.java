package com.checkout.instruments;

import com.checkout.common.CountryCode;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class InstrumentDetails {

    private String id;

    private String type;

    private String fingerprint;

    @SerializedName("expiry_month")
    private Integer expiryMonth;

    @SerializedName("expiry_year")
    private Integer expiryYear;

    private String name;

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

    @SerializedName("account_holder")
    private InstrumentAccountHolder accountHolder;

}
