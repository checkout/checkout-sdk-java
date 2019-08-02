package com.checkout.payments;

import lombok.Data;

@Data
public class CardDestinationResponse {
    private String id;
    private String type;
    private String bin;
    private String last4;
    private int expiryMonth;
    private int expiryYear;
    private String scheme;
    private String cardCategory;
    private String cardType;
    private String issuer;
    private String issuerCountry;
    private String productType;
    private String productId;
    private String fingerprint;
}
