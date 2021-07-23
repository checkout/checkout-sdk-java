package com.checkout;

public final class SecretKeyCredentials extends AbstractKeyCredentials implements ApiCredentials {

    /**
     * @deprecated Will be made private in a future version
     */
    @Deprecated
    public SecretKeyCredentials(final CheckoutConfiguration configuration) {
        super(configuration);
    }

    public static SecretKeyCredentials fromConfiguration(final CheckoutConfiguration configuration) {
        return new SecretKeyCredentials(configuration);
    }

    @Override
    public String getAuthorizationHeader() {
        return getHeader(configuration.getSecretKey());
    }

}