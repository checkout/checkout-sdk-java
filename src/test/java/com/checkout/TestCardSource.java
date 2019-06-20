package com.checkout;

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(int expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public int getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(int expiryYear) {
        this.expiryYear = expiryYear;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}