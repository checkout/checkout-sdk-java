package com.checkout.tokens;

import com.checkout.common.Address;
import com.checkout.common.Phone;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CardTokenResponse extends TokenResponse {
    private Address billingAddress;
    private Phone phone;
    private int expiryMonth;
    private int expiryYear;
    private String name;
    private String scheme;
    private String last4;
    private String bin;
    private String cardType;
    private String cardCategory;
    private String issuer;
    private String issuerCountry;
    private String productId;
    private String productType;
}