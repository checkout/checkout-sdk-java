package com.checkout;

final class PreviousStaticKeysSdkCredentials extends AbstractStaticKeysSdkCredentials {

    private static final String PREVIOUS_SECRET_KEY_PATTERN = "^sk_(((test_)?(\\w{8})-(\\w{4})-(\\w{4})-(\\w{4})-(\\w{12}))|((sbox_)?[a-z2-7]{26}[a-z2-7*#$=]))$";
    private static final String PREVIOUS_PUBLIC_KEY_PATTEN = "^pk_(((test_)?(\\w{8})-(\\w{4})-(\\w{4})-(\\w{4})-(\\w{12}))|((sbox_)?[a-z2-7]{26}[a-z2-7*#$=]))$";

    PreviousStaticKeysSdkCredentials(final String secretKey, final String publicKey) {
        super(PlatformType.PREVIOUS, PREVIOUS_SECRET_KEY_PATTERN, PREVIOUS_PUBLIC_KEY_PATTEN, secretKey, publicKey);
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
