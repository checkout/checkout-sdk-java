package com.checkout.payments;

import com.checkout.common.Address;
import com.checkout.common.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardSourceResponse implements ResponseSource {
    private String id;
    private String type;
    private Address billingAddress;
    private Phone phone;
    private int expiryMonth;
    private int expiryYear;
    private String name;
    private String scheme;
    private String last4;
    private String fingerprint;
    private String bin;
    private String cardType;
    private String cardCategory;
    private String issuer;
    private String issuerCountry;
    private String productId;
    private String productType;
    private String avsCheck;
    private String cvvCheck;
    private Boolean payouts;
    private String fastFunds;
    private String paymentAccountReference;

    @Override
    public String getType() {
        return type;
    }
}