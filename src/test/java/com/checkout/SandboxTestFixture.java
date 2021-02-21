package com.checkout;

public class SandboxTestFixture {

    public static final CheckoutConfiguration CONFIGURATION = loadConfiguration();
    private CheckoutApi api;

    public SandboxTestFixture() {
        api = new CheckoutApiImpl(new ApiClientImpl(CONFIGURATION), CONFIGURATION);
    }

    private static CheckoutConfiguration loadConfiguration() {
        String secretKey = System.getenv("CHECKOUT_SECRET_KEY");
        String publicKey = System.getenv("CHECKOUT_PUBLIC_KEY");

        if(secretKey == null) {
            throw new IllegalStateException("Please set the checkout.secretKey java variable!");
        }
        if(publicKey == null) {
            throw new IllegalStateException("Please set the checkout.publicKey java variable!");
        }

        CheckoutConfiguration configuration = new CheckoutConfiguration(secretKey, true);
        configuration.setPublicKey(publicKey);
        return configuration;
    }

    public CheckoutApi getApi() {
        return api;
    }
}