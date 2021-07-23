package com.checkout;

public final class PublicKeyCredentials extends AbstractKeyCredentials implements ApiCredentials {

    /**
     * @deprecated Will be made private in a future version
     */
    @Deprecated
    public PublicKeyCredentials(final CheckoutConfiguration configuration) {
        super(configuration);
    }

    public static PublicKeyCredentials fromConfiguration(final CheckoutConfiguration configuration) {
        return new PublicKeyCredentials(configuration);
    }

    @Override
    public String getAuthorizationHeader() {
        return getHeader(configuration.getPublicKey());
    }

}