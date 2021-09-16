package com.checkout.payments;

import com.checkout.common.Address;
import com.checkout.common.PaymentSourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import static com.checkout.common.CheckoutUtils.validateParams;

@Data
@Builder
@AllArgsConstructor
public class TokenSource implements RequestSource {

    private final PaymentSourceType type = PaymentSourceType.TOKEN;
    private final String token;
    private Address billingAddress;

    public TokenSource(final String token) {
        validateParams("token", token);
        this.token = token;
    }

}
