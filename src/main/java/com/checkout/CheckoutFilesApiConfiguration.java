package com.checkout;

public final class CheckoutFilesApiConfiguration {

    private final boolean enabled;

    private final Environment environment;

    private CheckoutFilesApiConfiguration(final boolean enabled, final Environment environment) {
        this.enabled = enabled;
        this.environment = environment;
    }

    public static CheckoutFilesApiConfiguration enabled(final Environment environment) {
        return new CheckoutFilesApiConfiguration(true, environment);
    }


    public static CheckoutFilesApiConfiguration disabled() {
        return new CheckoutFilesApiConfiguration(false, null);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Environment getEnvironment() {
        return environment;
    }

}

