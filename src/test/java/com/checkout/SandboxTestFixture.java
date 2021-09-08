package com.checkout;

import com.checkout.beta.Checkout;
import com.checkout.beta.CheckoutApi;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class SandboxTestFixture {

    protected static final String SELF = "self";

    private com.checkout.CheckoutApi api;
    private CheckoutApi apiV2;

    public SandboxTestFixture(final PlatformType platformType) {
        final CheckoutConfiguration configuration = loadConfiguration(platformType);
        switch (platformType) {
            case DEFAULT:
                this.api = new CheckoutApiImpl(configuration);
                break;
            case FOUR:
                this.apiV2 = Checkout.staticKeys()
                        .publicKey(configuration.getPublicKey())
                        .secretKey(configuration.getSecretKey())
                        .environment(Environment.SANDBOX)
                        .build();
                break;
        }
    }

    private CheckoutConfiguration loadConfiguration(final PlatformType platformType) {
        switch (platformType) {
            case DEFAULT:
                return new CheckoutConfiguration(requireNonNull(System.getenv("CHECKOUT_SECRET_KEY")), true, requireNonNull(System.getenv("CHECKOUT_PUBLIC_KEY")));
            case FOUR:
                return new CheckoutConfiguration(requireNonNull(System.getenv("CHECKOUT_FOUR_PUBLIC_KEY")), requireNonNull(System.getenv("CHECKOUT_FOUR_SECRET_KEY")), Environment.SANDBOX);
            default:
                throw new CheckoutArgumentException("unsupported configuration");
        }
    }

    protected <T> T blocking(final CompletableFuture<T> future) {
        try {
            return future.get();
        } catch (final Exception e) {
            fail(e.getMessage());
        }
        return null;
    }

    /**
     * Take a quick nap
     */
    protected void nap() {
        nap(2);
    }

    /**
     * Take a custom nap
     */
    protected void nap(final int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (final InterruptedException ignore) {
            fail();
        }
    }

    public com.checkout.CheckoutApi getApi() {
        return api;
    }

    public CheckoutApi getApiV2() {
        return apiV2;
    }

}