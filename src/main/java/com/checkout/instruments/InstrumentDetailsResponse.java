package com.checkout.instruments;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class InstrumentDetailsResponse {
    private String id;
    private String type;
    private String fingerprint;
    private Integer expiryMonth;
    private Integer expiryYear;
    private String name;
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
    private AccountHolder accountHolder;
    private CustomerResponse customer;
}
