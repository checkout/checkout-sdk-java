package com.checkout;

import java.util.stream.Stream;

import static com.checkout.common.CheckoutUtils.validateParams;

public enum Environment {

    SANDBOX("https://api.sandbox.checkout.com/"),
    PRODUCTION("https://api.checkout.com/");

    private final String uri;

    Environment(final String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public static Environment lookup(final String environment) {
        validateParams("environment", environment);
        return Stream.of(values())
                .filter(entry -> entry.name().equals(environment.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new CheckoutArgumentException("invalid environment property"));
    }

    /**
     * @deprecated Will be removed in a future version
     */
    @Deprecated
    public static Environment lookup(final boolean useSandbox) {
        return useSandbox ? SANDBOX : PRODUCTION;
    }

}
