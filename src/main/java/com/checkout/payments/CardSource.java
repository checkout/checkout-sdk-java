package com.checkout.payments;

import com.checkout.CheckoutArgumentException;
import com.checkout.common.Address;
import com.checkout.common.PaymentSourceType;
import com.checkout.common.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import static com.checkout.common.CheckoutUtils.validateParams;

@Data
@Builder
@AllArgsConstructor
public class CardSource implements RequestSource {

    private final PaymentSourceType type = PaymentSourceType.CARD;

    private final String number;

    private final Integer expiryMonth;

    private final Integer expiryYear;

    private String name;

    private String firstName;

    private String lastName;

    private String cvv;

    private Address billingAddress;

    private Phone phone;

    private Boolean stored;

    public CardSource(final String number, final int expiryMonth, final int expiryYear) {
        validateParams("number", number);
        if (expiryMonth < 1 || expiryMonth > 12) {
            throw new CheckoutArgumentException("The expiry month must be between 1 and 12");
        }
        this.number = number;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
    }

}
