package com.checkout.payments;

import com.checkout.common.Address;
import com.checkout.common.CheckoutUtils;
import com.checkout.common.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DLocalSource implements RequestSource {
    public static final String TYPE_NAME = "dlocal";
    private final String number;
    private final int expiryMonth;
    private final int expiryYear;
    private final String type = TYPE_NAME;
    private String name;
    private String firstName;
    private String lastName;
    private String cvv;
    private Address billingAddress;
    private Phone phone;
    private Boolean stored;

    public DLocalSource(String number, int expiryMonth, int expiryYear) {
        if (CheckoutUtils.isNullOrWhitespace(number)) {
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
        return type;
    }
}