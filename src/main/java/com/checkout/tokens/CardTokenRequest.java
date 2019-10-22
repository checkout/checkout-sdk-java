package com.checkout.tokens;

import com.checkout.common.Address;
import com.checkout.common.CheckoutUtils;
import com.checkout.common.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CardTokenRequest implements TokenRequest {
    private final String type = "card";
    private final String number;
    private final int expiryMonth;
    private final int expiryYear;
    private String name;
    private String cvv;
    private Address billingAddress;
    private Phone phone;

    public CardTokenRequest(String number, int expiryMonth, int expiryYear) {
        if (CheckoutUtils.isNullOrEmpty(number)) {
            throw new IllegalArgumentException("The card number is required.");
        }
        if (expiryMonth < 1 || expiryMonth > 12) {
            throw new IllegalArgumentException("The expiry month must be between 1 and 12");
        }
        this.number = number;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
    }

    @Override
    public String getType() {
        return "card";
    }
}