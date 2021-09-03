package com.checkout.payments;

import com.checkout.common.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import static com.checkout.common.CheckoutUtils.validateParams;

@Data
@Builder
@AllArgsConstructor
public class TokenSource implements RequestSource {

    private final String type = "token";
    private final String token;
    private Address billingAddress;

    public TokenSource(final String token) {
        validateParams("token", token);
        this.token = token;
    }

    @Override
    public String getType() {
        return type;
    }

}
