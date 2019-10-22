package com.checkout.payments;

import com.checkout.common.Address;
import com.checkout.common.CheckoutUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
public class TokenSource implements RequestSource {
    public static final String TYPE_NAME = "token";
    private final String token;
    private final String type = TYPE_NAME;
    private Address billingAddress;

    public TokenSource(String token) {
        if (CheckoutUtils.isNullOrWhitespace(token)) {
            throw new IllegalArgumentException("The token must be provided.");
        }

        this.token = token;
    }

    @Override
    public String getType() {
        return TYPE_NAME;
    }
}