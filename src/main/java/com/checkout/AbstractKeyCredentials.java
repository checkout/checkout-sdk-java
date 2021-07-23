package com.checkout;

import static com.checkout.common.CheckoutUtils.requiresNonNull;

public abstract class AbstractKeyCredentials {

    private static final String BEARER = "Bearer ";

    protected final CheckoutConfiguration configuration;

    public AbstractKeyCredentials(final CheckoutConfiguration configuration) {
        requiresNonNull("configuration", configuration);
        this.configuration = configuration;
    }

    protected String getHeader(final String key) {
        switch (configuration.getCustomerPlatformType()) {
            case CLASSIC:
                return key;
            case FOUR:
                return BEARER + key;
            default:
                throw new CheckoutArgumentException("Invalid customer platform type");
        }
    }

}