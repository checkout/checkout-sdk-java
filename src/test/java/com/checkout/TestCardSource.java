package com.checkout;

import lombok.Data;

@Data
public class TestCardSource {

    public static final TestCardSource VISA = new TestCardSource();

    static {
        VISA.number = "4242424242424242";
        VISA.expiryMonth = 6;
        VISA.expiryYear = 2025;
        VISA.cvv = "100";
    }

    private String number;
    private int expiryMonth;
    private int expiryYear;
    private String cvv;

}