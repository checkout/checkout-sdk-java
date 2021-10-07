package com.checkout.payments;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaymentType {

    public static final String REGULAR = "Regular";
    public static final String RECURRING = "Recurring";
    public static final String MOTO = "Moto";

}