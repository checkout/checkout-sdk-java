package com.checkout.instruments;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class CreateInstrumentResponse {
    private String id;
    private String type;
    private Integer expiryMonth;
    private Integer expiryYear;
    private String scheme;
    @SerializedName("last4")
    private String last4;
    private String bin;
    private String cardType;
    private String cardCategory;
    private String issuer;
    private String issuerCountry;
    private String productId;
    private String productType;
}
