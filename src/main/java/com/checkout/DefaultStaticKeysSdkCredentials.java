package com.checkout;

final class DefaultStaticKeysSdkCredentials extends AbstractStaticKeysSdkCredentials {

    private static final String DEFAULT_SECRET_KEY_PATTERN = "^sk_(test_)?(\\w{8})-(\\w{4})-(\\w{4})-(\\w{4})-(\\w{12})$";
    private static final String DEFAULT_PUBLIC_KEY_PATTEN = "^pk_(test_)?(\\w{8})-(\\w{4})-(\\w{4})-(\\w{4})-(\\w{12})$";

    DefaultStaticKeysSdkCredentials(final String secretKey, final String publicKey) {
        super(PlatformType.DEFAULT, DEFAULT_SECRET_KEY_PATTERN, DEFAULT_PUBLIC_KEY_PATTEN, secretKey, publicKey);
    }

    @Override
    public SdkAuthorization getAuthorization(final SdkAuthorizationType authorizationType) {
        switch (authorizationType) {
            case SECRET_KEY:
                return new SdkAuthorization(platformType, secretKey);
            case PUBLIC_KEY:
                return new SdkAuthorization(platformType, publicKey);
            default:
                throw CheckoutAuthorizationException.invalidAuthorization(authorizationType);
        }
    }

}
