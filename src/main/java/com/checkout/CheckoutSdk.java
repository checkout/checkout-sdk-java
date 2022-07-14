package com.checkout;

public final class CheckoutSdk {

    private CheckoutSdk() {
    }

    public static CheckoutSdkBuilder builder() {
        return new CheckoutSdkBuilder();
    }

}