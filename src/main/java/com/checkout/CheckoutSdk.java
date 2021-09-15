package com.checkout;

public final class CheckoutSdk {

    private CheckoutSdk() {
    }

    public static CheckoutDefaultSdk defaultSdk() {
        return new CheckoutDefaultSdk();
    }

    public static CheckoutFourSdk fourSdk() {
        return new CheckoutFourSdk();
    }

}
